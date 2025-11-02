package pt.psoft.g1.psoftg1.lendingmanagement.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.Test;

import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

public class LendingTransparentBoxTest {
    @Test
    void testLendingNumberInvalidYearThrows() {
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(1960, 1));
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(LocalDate.now().getYear() + 1, 1));
    }

    @Test
    void testLendingNumberNegativeSequentialThrows() {
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(-5));
    }

    @Test
    void testLendingNumberInvalidFormatStringThrows() {
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber("2023-5"));
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber("abcd/efg"));
    }

    @Test
    void testFineCannotBeCreatedForNonOverdueLending() {
        Book book = new Book();
        ReaderDetails reader = new ReaderDetails();
        Lending lending = new Lending(book, reader, new LendingNumber(2023, 1),
                LocalDate.now().minusDays(2), LocalDate.now(), 100);

        assertThrows(IllegalArgumentException.class, () -> new Fine(lending));
    }

    @Test
    void testSetReturnedThrowsIfAlreadyReturned() {
        Book book = new Book();
        ReaderDetails reader = new ReaderDetails();
        Lending lending = new Lending(book, reader, new LendingNumber(2023, 1),
                LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), 100);

        lending.setReturned("First return");
        assertThrows(IllegalArgumentException.class, () -> lending.setReturned("Second return"));
    }
}
