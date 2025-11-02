package pt.psoft.g1.psoftg1.bookmanagement.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Isbn {

    private final String isbn;

    public Isbn(String isbn) {
        if (isbn == null)
            throw new IllegalArgumentException("ISBN cannot be null");
        if (!isValidIsbn(isbn))
            throw new IllegalArgumentException("Invalid ISBN-10 or ISBN-13 format");
        this.isbn = isbn;
    }

    protected Isbn() {
        this.isbn = null; // ORM-safe
    }

    private static boolean isValidIsbn(String isbn) {
        return (isbn.length() == 10) ? isValidIsbn10(isbn) : isValidIsbn13(isbn);
    }

    private static boolean isValidIsbn10(String isbn) {
        if (!isbn.matches("\\d{9}[\\dX]")) return false;
        int sum = 0;
        for (int i = 0; i < 9; i++)
            sum += (isbn.charAt(i) - '0') * (10 - i);
        char lastChar = isbn.charAt(9);
        int lastDigit = (lastChar == 'X') ? 10 : lastChar - '0';
        sum += lastDigit;
        return sum % 11 == 0;
    }

    private static boolean isValidIsbn13(String isbn) {
        if (!isbn.matches("\\d{13}")) return false;
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checksum = (10 - (sum % 10)) % 10;
        return checksum == Character.getNumericValue(isbn.charAt(12));
    }

    @Override
    public String toString() {
        return this.isbn;
    }
}
