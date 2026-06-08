package org.example;

public record Judet(String isoCode, String nume, String regiune, long populatie, long suprafata) {

    public double getDensitate() {
        return (double) populatie / suprafata;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %d %d", isoCode, nume, regiune, populatie, suprafata);
    }
}