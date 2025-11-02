package pt.psoft.g1.psoftg1.authormanagement.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import pt.psoft.g1.psoftg1.shared.model.StringUtilsCustom;

public class AuthorTransparentBoxTest {
    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("João", "teste", "photo1.jpg");
        author.setVersion(1L);
    }

    @Test
    void testSetNameUpdatesInternalState() {
        author.setName("Joana");
        assertEquals("Joana", author.getName());
    }

    @Test
    void testSetBioUpdatesInternalState() {
        author.setBio("teste 1");
        assertEquals("teste 1", author.getBio());
    }

    @Test
    void testBioToStringReturnsSanitizedValue() {
        Bio bio = new Bio("<b>bold</b>");
        assertEquals(StringUtilsCustom.sanitizeHtml("<b>bold</b>"), bio.toString());
    }

    @Test
    void testBioInvalidValuesThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Bio(null));
        assertThrows(IllegalArgumentException.class, () -> new Bio(""));
        assertThrows(IllegalArgumentException.class, () -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 5000; i++) sb.append("a");
            new Bio(sb.toString());
        });
    }
}
