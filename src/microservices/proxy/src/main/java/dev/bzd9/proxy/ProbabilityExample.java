package dev.bzd9.proxy;

import java.util.Random;

public class ProbabilityExample {
    public static void main(String[] args) {
        Random random = new Random();

        // Test multiple times to see the probability in action
        int countVariantA = 0;
        int countVariantB = 0;
        int totalTrials = 1_000_000;

        for (int i = 0; i < totalTrials; i++) {
            String chosen = chooseVariant(random);

            if (chosen.equals("Variant A")) {
                countVariantA++;
            } else {
                countVariantB++;
            }
        }

        System.out.println("Results after " + totalTrials + " trials:");
        System.out.println("Variant A: " + countVariantA + " times (" +
                (countVariantA * 100.0 / totalTrials) + "%)");
        System.out.println("Variant B: " + countVariantB + " times (" +
                (countVariantB * 100.0 / totalTrials) + "%)");
    }

    public static String chooseVariant(Random random) {
        // Generate random number between 0.0 (inclusive) and 1.0 (exclusive)
        double randomValue = random.nextDouble();

        // 81% probability for Variant A, 19% for Variant B
        if (randomValue < 0.81) {
            return "Variant A";
        } else {
            return "Variant B";
        }
    }
}
