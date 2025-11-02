package pt.psoft.g1.psoftg1.readermanagement.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;

import org.junit.Test;

public class ReaderDetailsMutationTest {
    @Test
    void mutationInvalidPhoneNumberShouldFail() {
        assertThrows(IllegalArgumentException.class, () -> new PhoneNumber("712345678"));
    }

    @Test
    void mutationUnderageBirthDateShouldFail() {
        int currentYear = LocalDate.now().getYear();
        String underage = (currentYear - 5) + "-01-01";
        assertThrows(AccessDeniedException.class, () -> new BirthDate(underage));
    }

    /*@Test
    void mutationReaderDetailsGDPRFalseShouldFail() {
        Reader reader = new Reader();
        assertThrows(IllegalArgumentException.class, () ->
                new ReaderDetails(1, reader, "1990-01-01", "912345678", false, true, true, null, List.of()));
    }

    @Test
    void mutationPatchWithWrongVersionShouldFail() {
        Reader reader = new Reader();
        ReaderDetails rd = new ReaderDetails(
                1, reader, "1990-01-01", "912345678", true, false, false, null, List.of()
        );
        rd.setVersion(1L);

        UpdateReaderRequest req = new UpdateReaderRequest();
        assertThrows(ConflictException.class, () -> rd.applyPatch(2L, req, null, null));
    }*/
}
