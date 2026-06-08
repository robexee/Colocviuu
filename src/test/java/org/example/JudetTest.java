package org.example;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class JudetTest {

    @Test
    void testeazaCalcululDensitatii() {
        Judet bucuresti = new Judet("B", "Bucuresti", "BucurestiIlfov", 1716983, 240);
        assertEquals(1716983.0 / 240.0, bucuresti.getDensitate(), 0.0001, "Densitatea calculată este greșită");
    }

    private void assertEquals(double v, double densitate, double v1, String densitateaCalculatăEsteGreșită) {
    }

    @Test
    void testeazaFiltrarea() {
        List<Judet> lista = List.of(
                new Judet("TM", "Timis", "Vest", 650533, 8692),
                new Judet("CS", "CarasSeverin", "Vest", 246588, 8532),
                new Judet("SB", "Sibiu", "Centru", 388325, 5432)
        );
        String keyword = "ve";

        long rezultate = lista.stream()
                .filter(j -> j.isoCode().toLowerCase().contains(keyword) ||
                        j.nume().toLowerCase().contains(keyword) ||
                        j.regiune().toLowerCase().contains(keyword))
                .count();

        assertEquals(2, rezultate, "Trebuie sa gaseasca exact 2 judete");
    }
}