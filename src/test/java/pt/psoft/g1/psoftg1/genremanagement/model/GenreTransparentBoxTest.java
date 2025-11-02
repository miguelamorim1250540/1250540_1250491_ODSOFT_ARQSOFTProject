package pt.psoft.g1.psoftg1.genremanagement.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class GenreTransparentBoxTest {
    @Test
    void testValidGenreCreation() {
        assertDoesNotThrow(() -> new Genre("id1", "Mystery"));
    }

    @Test
    void testGenreCannotBeNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Genre("1", null));
        assertEquals("Genre cannot be null", exception.getMessage());
    }

    @Test
    void testGenreCannotBeBlank() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Genre("1", "  "));
        assertEquals("Genre cannot be blank", exception.getMessage());
    }

    @Test
    void testGenreCannotExceedMaxLength() {
        String longGenre = "A".repeat(101);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Genre("1", longGenre));
        assertEquals("Genre has a maximum of 100 characters", exception.getMessage());
    }
}
