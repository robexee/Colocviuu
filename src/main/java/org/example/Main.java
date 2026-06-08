package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    interface OutputStrategy {
        void write(List<Judet> judete) throws IOException;
    }

    public static void main(String[] args) {
        OutputStrategy consoleStrategy = judete -> {
            judete.forEach(System.out::println);
            System.out.println();
        };

        OutputStrategy fileStrategy = judete -> {
            Path path = Path.of("output.txt");
            for (Judet j : judete) {
                Files.writeString(path, j.toString() + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
            Files.writeString(path, System.lineSeparator(), StandardOpenOption.APPEND);
        };

        try { Files.deleteIfExists(Path.of("output.txt")); } catch (IOException ignored) {}

        OutputStrategy strategy = fileStrategy;

        try {
            List<Judet> judete = citesteJudete("src/main/java/org/example/judete.txt");

            List<Judet> sortate = judete.stream()
                    .sorted(Comparator.comparingDouble(Judet::getDensitate).reversed())
                    .toList();
            strategy.write(sortate);

            Path searchPath = Path.of("search.txt");
            if (Files.exists(searchPath)) {
                String keyword = Files.readString(searchPath).trim().toLowerCase();
                List<Judet> filtrate = judete.stream()
                        .filter(j -> j.isoCode().toLowerCase().contains(keyword) ||
                                j.nume().toLowerCase().contains(keyword) ||
                                j.regiune().toLowerCase().contains(keyword))
                        .toList();
                strategy.write(filtrate);
            }

        } catch (IOException e) {
            System.err.println("Eroare la procesarea fisierelor: " + e.getMessage());
        }
    }

    private static List<Judet> citesteJudete(String filename) throws IOException {
        List<Judet> list = new ArrayList<>();
        for (String line : Files.readAllLines(Path.of(filename))) {
            if (line.trim().isEmpty()) continue;
            String[] p = line.split("\\s+");
            list.add(new Judet(p[0], p[1], p[2], Long.parseLong(p[3]), Long.parseLong(p[4])));
        }
        return list;
    }
}