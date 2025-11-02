package pt.psoft.g1.psoftg1.readermanagement.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;

import org.junit.Test;

public class ReaderDetailsTransparentBoxTest {
    @Test
    void testPhoneNumberInvalidThrows() {
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("12345678"));
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("812345678"));
    }

    @Test
    void testBirthDateInvalidFormatThrows() {
        assertThrows(IllegalArgumentException.class, () -> new BirthDate("01-01-2000"));
    }

    @Test
    void testBirthDateUnderageThrows() {
        int currentYear = LocalDate.now().getYear();
        String underage = (currentYear - 10) + "-01-01"; // assuming minimumAge > 10
        assertThrows(AccessDeniedException.class, () -> new BirthDate(underage));
    }

    /*@Test
    void testReaderDetailsGDPRFalseThrows() {
        ReaderDetails reader = new ReaderDetails();
        List<Genre> interests = List.of();

        assertThrows(IllegalArgumentException.class, () ->
                new ReaderDetails(1, reader, "1990-01-01", "912345678", false, false, false, null, interests));
    }

    @Test
    void testApplyPatchVersionMismatchThrows() {
        ReaderDetails reader = new ReaderDetails();
        ReaderDetails rd = new ReaderDetails(
                1, reader, "1990-01-01", "912345678", true, true, true, null, List.of()
        );
        rd.setVersion(1L);

        UpdateReaderRequest req = new UpdateReaderRequest();
        assertThrows(ConflictException.class, () -> rd.applyPatch(2L, req, null, null));
    }*/
}
