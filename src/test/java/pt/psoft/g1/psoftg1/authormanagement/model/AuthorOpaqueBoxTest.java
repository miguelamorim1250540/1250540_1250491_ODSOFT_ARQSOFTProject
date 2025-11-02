package pt.psoft.g1.psoftg1.authormanagement.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hibernate.StaleObjectStateException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import pt.psoft.g1.psoftg1.authormanagement.services.UpdateAuthorRequest;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;

public class AuthorOpaqueBoxTest {
    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("João", "teste", "photo1.jpg");
        author.setVersion(1L);
        author.setAuthorNumber("A123");
    }

    @Test
    void testGettersReturnCorrectValues() {
        assertEquals("João", author.getName());
        assertEquals("teste", author.getBio());
        assertEquals("photo1.jpg", author.getPhotoInternal());
    }

    @Test
    void testApplyPatchUpdatesFields() {
        UpdateAuthorRequest patch = new UpdateAuthorRequest();
        patch.setName("Joana");
        patch.setBio("teste 1");
        patch.setPhotoURI("photo2.jpg");

        author.applyPatch(1L, patch);

        assertEquals("Joana", author.getName());
        assertEquals("teste 1", author.getBio());
        assertEquals("photo2.jpg", author.getPhotoInternal());
    }

    @Test
    void testApplyPatchWithWrongVersionThrows() {
        UpdateAuthorRequest patch = new UpdateAuthorRequest();
        patch.setName("Joana");
        assertThrows(StaleObjectStateException.class, () -> author.applyPatch(2L, patch));
    }

    @Test
    void testRemovePhotoWithCorrectVersion() {
        author.removePhoto(1L);
        assertNull(author.getPhotoInternal());
    }

    @Test
    void testRemovePhotoWithWrongVersionThrows() {
        assertThrows(ConflictException.class, () -> author.removePhoto(2L));
    }
}
