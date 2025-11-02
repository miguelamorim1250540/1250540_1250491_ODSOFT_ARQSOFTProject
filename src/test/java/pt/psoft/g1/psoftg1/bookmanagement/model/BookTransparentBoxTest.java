package pt.psoft.g1.psoftg1.bookmanagement.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class BookTransparentBoxTest {
    @Test
    void testTitleSanitization() {
        Title title = new Title(" <b>Bold Title</b> ");
        assertEquals("Bold Title", title.getTitle());
    }

    @Test
    void testTitleInvalidValuesThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Title(null));
        assertThrows(IllegalArgumentException.class, () -> new Title(""));
        assertThrows(IllegalArgumentException.class, () -> new Title(" ".repeat(200)));
    }

    @Test
    void testDescriptionSanitization() {
        Description description = new Description(" <script>alert('x')</script>Valid Description ");
        assertEquals("Valid Description", description.toString());
    }

    @Test
    void testDescriptionInvalidValuesThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Description(" ".repeat(5000)));
    }

    @Test
    void testIsbnValidation() {
        assertDoesNotThrow(() -> new Isbn("123456789X")); // valid ISBN-10
        assertDoesNotThrow(() -> new Isbn("9780306406157")); // valid ISBN-13
        assertThrows(IllegalArgumentException.class, () -> new Isbn("invalidisbn"));
    }
}
