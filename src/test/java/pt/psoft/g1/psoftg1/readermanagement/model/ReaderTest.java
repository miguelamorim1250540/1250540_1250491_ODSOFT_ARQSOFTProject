package pt.psoft.g1.psoftg1.readermanagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.shared.model.Photo;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ReaderTest {
    @Test
    void ensureValidReaderDetailsAreCreated() {
        Reader mockReader = mock(Reader.class);
        assertDoesNotThrow(() -> new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false,null, null));
    }

    @Test
    void ensureExceptionIsThrownForNullReader() {
        assertThrows(IllegalArgumentException.class, () -> new ReaderDetails(123, null, "2010-01-01", "912345678", true, false, false,null,null));
    }

    @Test
    void ensureExceptionIsThrownForNullPhoneNumber() {
        Reader mockReader = mock(Reader.class);
        assertThrows(IllegalArgumentException.class, () -> new ReaderDetails(123, mockReader, "2010-01-01", null, true, false, false,null,null));
    }

    @Test
    void ensureExceptionIsThrownForNoGdprConsent() {
        Reader mockReader = mock(Reader.class);
        assertThrows(IllegalArgumentException.class, () -> new ReaderDetails(123, mockReader, "2010-01-01", "912345678", false, false, false,null,null));
    }

    @Test
    void ensureGdprConsentIsTrue() {
        Reader mockReader = mock(Reader.class);
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false,null,null);
        assertTrue(readerDetails.isGdprConsent());
    }

    @Test
    void ensurePhotoCanBeNull_AkaOptional() {
        Reader mockReader = mock(Reader.class);
        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false,null,null);
        assertNull(readerDetails.getPhotoURI());
    }

    @Test
    void ensureValidPhoto() {
        Reader mockReader = mock(Reader.class);
        ReaderDetails readerDetails = new ReaderDetails(
            123, mockReader, "2010-01-01", "912345678", true, false, false, "readerPhotoTest.jpg", null
        );

        String photoURI = readerDetails.getPhotoURI();

        assertNotNull(photoURI); // o URI não pode ser nulo
        assertEquals("readerPhotoTest.jpg", photoURI); // compara diretamente com a string
    }

    @Test
    void ensureInterestListCanBeNullOrEmptyList_AkaOptional() {
        Reader mockReader = mock(Reader.class);
        ReaderDetails readerDetailsNullInterestList = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false,"readerPhotoTest.jpg",null);
        assertNull(readerDetailsNullInterestList.getInterestList());

        ReaderDetails readerDetailsInterestListEmpty = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false,"readerPhotoTest.jpg",new ArrayList<>());
        assertEquals(0, readerDetailsInterestListEmpty.getInterestList().size());
    }

    @Test
    void ensureInterestListCanTakeAnyValidGenre() {
        Reader mockReader = mock(Reader.class);
        Genre g1 = new Genre("genre1", null);
        Genre g2 = new Genre("genre2", null);
        List<Genre> genreList = new ArrayList<>();
        genreList.add(g1);
        genreList.add(g2);

        ReaderDetails readerDetails = new ReaderDetails(123, mockReader, "2010-01-01", "912345678", true, false, false,"readerPhotoTest.jpg",genreList);
        assertEquals(2, readerDetails.getInterestList().size());
    }
}
