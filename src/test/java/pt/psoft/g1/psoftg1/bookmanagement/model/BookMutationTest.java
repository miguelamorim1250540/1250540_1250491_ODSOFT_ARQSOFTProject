package pt.psoft.g1.psoftg1.bookmanagement.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.hibernate.StaleObjectStateException;
import org.junit.Test;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

public class BookMutationTest {
    @Test
    void mutationApplyPatchVersionCheck() {
        Book book = new Book("123456789X", "Title", "Description", new Genre("id1", "Fiction"), List.of(new Author("John Doe", "Bio", null)), "photo.jpg");
        book.setVersion(1L);

        UpdateBookRequest request = new UpdateBookRequest();
        request.setTitle("Mutated Title");

        // Mutation: ignoring version check should fail test
        assertThrows(StaleObjectStateException.class, () -> book.applyPatch(2L, request));
    }

    @Test
    void mutationRemovePhotoVersionCheck() {
        Book book = new Book("123456789X", "Title", "Description", new Genre("id1", "Fiction"), List.of(new Author("John Doe", "Bio", null)), "photo.jpg");
        book.setVersion(1L);

        // Mutation: removing photo with wrong version should fail
        assertThrows(ConflictException.class, () -> book.removePhoto(2L));
    }
}
