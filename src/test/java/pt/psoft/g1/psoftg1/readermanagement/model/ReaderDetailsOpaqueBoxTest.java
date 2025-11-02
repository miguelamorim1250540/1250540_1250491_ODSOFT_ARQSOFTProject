package pt.psoft.g1.psoftg1.readermanagement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class ReaderDetailsOpaqueBoxTest {
    @Test
    void testReaderNumberString() {
        ReaderNumber rn = new ReaderNumber(2023, 5);
        assertEquals("2023/5", rn.toString());

        ReaderNumber rn2 = new ReaderNumber(10);
        assertTrue(rn2.toString().matches("\\d{4}/10"));
    }

    @Test
    void testPhoneNumberValid() {
        PhoneNumber phone = new PhoneNumber("912345678");
        assertEquals("912345678", phone.toString());
    }

    @Test
    void testBirthDateValid() {
        BirthDate bd = new BirthDate("2000-01-01");
        assertEquals("2000-1-1", bd.toString());
    }

    /*@Test
    void testCreateReaderDetailsSuccessfully() {
        ReaderDetails reader = new ReaderDetails();
        List<Genre> interests = List.of(new Genre("1", "Fantasy"));

        ReaderDetails rd = new ReaderDetails(
                1,
                reader,
                "1990-05-20",
                "912345678",
                true,
                false,
                false,
                null,
                interests
        );

        assertEquals("912345678", rd.getPhoneNumber());
        assertEquals("1990-5-20", rd.getBirthDate().toString());
        assertEquals("2023/1", rd.getReaderNumber().substring(0, 4) + "/1".substring(0,1)); // current year / 1
    }*/
}
