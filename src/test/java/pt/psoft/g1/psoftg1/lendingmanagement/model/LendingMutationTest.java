package pt.psoft.g1.psoftg1.lendingmanagement.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.Test;

import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

public class LendingMutationTest {
    @Test
    void mutationFineCreatedForNonOverdueLendingShouldFail() {
        Book book = new Book();
        ReaderDetails reader = new ReaderDetails();
        Lending lending = new Lending(book, reader, new LendingNumber(2023, 1),
                LocalDate.now(), LocalDate.now().plusDays(5), 100);

        assertThrows(IllegalArgumentException.class, () -> new Fine(lending));
    }

    @Test
    void mutationLendingNumberWithInvalidSequentialShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber(-1));
    }

    @Test
    void mutationLendingNumberWithWrongFormatStringShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> new LendingNumber("2023/abc"));
    }

    @Test
    void mutationSetReturnedOnAlreadyReturnedLendingShouldFail() {
        Book book = new Book();
        ReaderDetails reader = new ReaderDetails();
        Lending lending = new Lending(book, reader, new LendingNumber(2023, 1),
                LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), 100);

        lending.setReturned("Returned once");
        assertThrows(IllegalArgumentException.class, () -> lending.setReturned("Returned again"));
    }
}
