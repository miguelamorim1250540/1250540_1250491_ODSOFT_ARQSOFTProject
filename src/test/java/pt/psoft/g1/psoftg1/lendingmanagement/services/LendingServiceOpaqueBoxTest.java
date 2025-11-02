package pt.psoft.g1.psoftg1.lendingmanagement.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.exceptions.LendingForbiddenException;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.FineRepository;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.LendingRepository;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;

@ExtendWith(MockitoExtension.class)
public class LendingServiceOpaqueBoxTest {
    @Mock private LendingRepository lendingRepository;
    @Mock private FineRepository fineRepository;
    @Mock private BookRepository bookRepository;
    @Mock private ReaderRepository readerRepository;

    @InjectMocks private LendingServiceImpl lendingService;

    @Test
    void testFindByLendingNumberReturnsOptional() {
        Lending lending = mock(Lending.class);
        when(lendingRepository.findByLendingNumber("2025/1")).thenReturn(Optional.of(lending));

        Optional<Lending> result = lendingService.findByLendingNumber("2025/1");

        assertTrue(result.isPresent());
    }

    @Test
    void testCreateLendingWithOverdueThrows() {
        CreateLendingRequest request = new CreateLendingRequest();
        request.setReaderNumber("2025/1");
        request.setIsbn("ISBN-1");

        Lending overdue = mock(Lending.class);
        when(overdue.getDaysDelayed()).thenReturn(1);
        when(lendingRepository.listOutstandingByReaderNumber("2025/1")).thenReturn(List.of(overdue));

        assertThrows(LendingForbiddenException.class, () -> lendingService.create(request));
    }
}
