package pt.psoft.g1.psoftg1.genremanagement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class GenreOpaqueBoxTest {
    @Test
    void testGettersReturnCorrectValues() {
        Genre genre = new Genre("id1", "Fiction");

        assertEquals("Fiction", genre.getGenre());
        assertEquals("id1", genre.getId());
    }

    @Test
    void testToStringReturnsGenre() {
        Genre genre = new Genre("id1", "Science");
        assertEquals("Science", genre.toString());
    }
}
