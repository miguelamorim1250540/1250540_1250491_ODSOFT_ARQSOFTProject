package pt.psoft.g1.psoftg1.bookmanagement.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.hibernate.StaleObjectStateException;
import org.junit.Test;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

public class BookOpaqueBoxTest {
    @Test
    void testGettersReturnCorrectValues() {
        Book book = new Book("123456789X", "Test Title", "Test Description", new Genre("id1", "Fiction"), List.of(new Author("John Doe", "Bio", null)), "photo.jpg");

        assertEquals("123456789X", book.getIsbn());
        assertEquals("Test Title", book.getTitle().getTitle());
        assertEquals("Test Description", book.getDescription());
    }

    @Test
    void testApplyPatchUpdatesFields() {
        Book book = new Book("123456789X", "Old Title", "Old Description", new Genre("id1", "Fiction"), List.of(new Author("John Doe", "Bio", null)), "photo.jpg");
        book.setVersion(1L);

        UpdateBookRequest request = new UpdateBookRequest();
        request.setTitle("New Title");
        request.setDescription("New Description");

        book.applyPatch(1L, request);

        assertEquals("New Title", book.getTitle().getTitle());
        assertEquals("New Description", book.getDescription());
    }

    @Test
    void testApplyPatchWithWrongVersionThrowsException() {
        Book book = new Book("123456789X", "Title", "Description", new Genre("id1", "Fiction"), List.of(new Author("John Doe", "Bio", null)), "photo.jpg");
        book.setVersion(1L);

        UpdateBookRequest request = new UpdateBookRequest();
        request.setTitle("Updated Title");

        assertThrows(StaleObjectStateException.class, () -> book.applyPatch(2L, request));
    }

    @Test
    void testRemovePhotoWithCorrectVersion() {
        Book book = new Book("123456789X", "Title", "Description", new Genre("id1", "Fiction"), List.of(new Author("John Doe", "Bio", null)), "photo.jpg");
        book.setVersion(1L);
        book.removePhoto(1L);

        assertNull(book.getPhoto());
    }

    @Test
    void testRemovePhotoWithWrongVersionThrowsException() {
        Book book = new Book("123456789X", "Title", "Description", new Genre("id1", "Fiction"), List.of(new Author("John Doe", "Bio", null)), "photo.jpg");
        book.setVersion(1L);

        assertThrows(ConflictException.class, () -> book.removePhoto(2L));
    }
}
