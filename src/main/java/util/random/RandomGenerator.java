package util.random;

import java.util.Random;

public class RandomGenerator {


    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int IBAN_LENGTH = 22;

    public static String generateRandomIBAN() {
        StringBuilder iban = new StringBuilder();
        iban.append(randomUpperCaseLetter()).append(randomUpperCaseLetter());
        iban.append(randomDigit()).append(randomDigit());
        for (int i = 4; i < IBAN_LENGTH; i++) {
            iban.append(randomDigit());
        }
        return iban.toString();
    }

    public static String generateRandomAccountNummber() {
        StringBuilder digits = new StringBuilder();
        for (int i = 0; i < 21; i++) {
            digits.append(randomDigit());
        }
        return digits.toString();
    }

    private static char randomUpperCaseLetter() {
        Random random = new Random();
        return ALPHABET.charAt(random.nextInt(ALPHABET.length()));
    }

    private static char randomDigit() {
        Random random = new Random();
        return (char) ('0' + random.nextInt(10));
    }


}

