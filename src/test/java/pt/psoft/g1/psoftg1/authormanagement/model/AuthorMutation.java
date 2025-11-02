package pt.psoft.g1.psoftg1.authormanagement.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hibernate.StaleObjectStateException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import pt.psoft.g1.psoftg1.authormanagement.services.UpdateAuthorRequest;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;

public class AuthorMutation {
    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("João", "teste", "photo1.jpg");
        author.setVersion(1L);
    }

    @Test
    void mutationTestApplyPatchVersionCheck() {
        UpdateAuthorRequest patch = new UpdateAuthorRequest();
        patch.setName("Mutated Name");
        assertThrows(StaleObjectStateException.class, () -> author.applyPatch(999L, patch));
    }

    @Test
    void mutationTestRemovePhotoVersionCheck() {
        assertThrows(ConflictException.class, () -> author.removePhoto(999L));
    }
}
