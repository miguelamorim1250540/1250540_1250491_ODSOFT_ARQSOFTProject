package pt.psoft.g1.psoftg1.genremanagement.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class GenreMutationTest {
    @Test
    void mutationGenreNullValueShouldFail() {
        // Mutation: setting genre to null should be caught by tests
        assertThrows(IllegalArgumentException.class, () -> new Genre("id1", null));
    }

    @Test
    void mutationGenreBlankValueShouldFail() {
        // Mutation: setting genre to blank should fail
        assertThrows(IllegalArgumentException.class, () -> new Genre("id1", "   "));
    }

    @Test
    void mutationGenreTooLongShouldFail() {
        // Mutation: genre length > 100 should fail
        String tooLong = "B".repeat(200);
        assertThrows(IllegalArgumentException.class, () -> new Genre("id1", tooLong));
    }
}
