package pt.psoft.g1.psoftg1.lendingmanagement.model;

import java.io.Serializable;
import java.time.LocalDate;

public class LendingNumber implements Serializable {

    private final String lendingNumber;

    public LendingNumber(int year, int sequential) {
        if (year < 1970 || LocalDate.now().getYear() < year)
            throw new IllegalArgumentException("Invalid year component");
        if (sequential < 0)
            throw new IllegalArgumentException("Sequential component cannot be negative");
        this.lendingNumber = year + "/" + sequential;
    }

    public LendingNumber(String lendingNumber) {
        if (lendingNumber == null)
            throw new IllegalArgumentException("Lending number cannot be null");

        int year, sequential;
        try {
            year = Integer.parseInt(lendingNumber, 0, 4, 10);
            sequential = Integer.parseInt(lendingNumber, 5, lendingNumber.length(), 10);
            if (lendingNumber.charAt(4) != '/')
                throw new IllegalArgumentException("Wrong format. Should be \"{year}/{sequential}\"");
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Wrong format. Should be \"{year}/{sequential}\"");
        }
        this.lendingNumber = year + "/" + sequential;
    }

    public LendingNumber(int sequential) {
        if (sequential < 0)
            throw new IllegalArgumentException("Sequential component cannot be negative");
        this.lendingNumber = LocalDate.now().getYear() + "/" + sequential;
    }

    @Override
    public String toString() {
        return this.lendingNumber;
    }
}
