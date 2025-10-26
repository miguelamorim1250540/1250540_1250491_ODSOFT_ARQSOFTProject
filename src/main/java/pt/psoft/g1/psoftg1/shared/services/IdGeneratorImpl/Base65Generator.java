package pt.psoft.g1.psoftg1.shared.services.IdGeneratorImpl;

import java.security.SecureRandom;

import org.springframework.context.annotation.Profile;

import pt.psoft.g1.psoftg1.shared.services.IdGenerator;

@Profile("base65")
public class Base65Generator implements IdGenerator{

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_.@";
    private static final int BASE = ALPHABET.length();
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public String generate() {
        long randomNumber = Math.abs(RANDOM.nextLong());
        return encodeBase65(randomNumber);
    }

    private String encodeBase65(long number) {
        if (number == 0) {
            return String.valueOf(ALPHABET.charAt(0));
        }

        StringBuilder encoded = new StringBuilder();
        while (number > 0) {
            int remainder = (int) (number % BASE);
            encoded.insert(0, ALPHABET.charAt(remainder));
            number /= BASE;
        }
        return encoded.toString();
    }
    
}
