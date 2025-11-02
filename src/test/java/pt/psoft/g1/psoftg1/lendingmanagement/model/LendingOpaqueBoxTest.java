package pt.psoft.g1.psoftg1.lendingmanagement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;

import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

public class LendingOpaqueBoxTest {
    @Test
    void testLendingNumberString() {
        LendingNumber ln = new LendingNumber(2023, 5);
        assertEquals("2023/5", ln.toString());
    }

    @Test
    void testFineValueCalculationForOverdue() {
        Book book = new Book();
        ReaderDetails reader = new ReaderDetails();
        Lending lending = new Lending(book, reader, new LendingNumber(2023, 1),
                LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), 100);

        Fine fine = new Fine(lending);
        assertEquals(500, fine.getCentsValue()); // 5 days overdue * 100 cents
    }

    @Test
    void testGetDaysDelayed() {
        Book book = new Book();
        ReaderDetails reader = new ReaderDetails();
        Lending lending = new Lending(book, reader, new LendingNumber(2023, 1),
                LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), 100);

        assertEquals(5, lending.getDaysDelayed());
    }

    @Test
    void testSetReturned() {
        Book book = new Book();
        ReaderDetails reader = new ReaderDetails();
        Lending lending = new Lending(book, reader, new LendingNumber(2023, 1),
                LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), 100);

        lending.setReturned("Returned on time");
        assertNotNull(lending.getDaysDelayed());
    }
}
