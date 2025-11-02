package pt.psoft.g1.psoftg1.bookmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookServiceImpl;
import pt.psoft.g1.psoftg1.bookmanagement.services.CreateBookRequest;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.shared.model.IDGeneratorFactory;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceOpaqueTest {
    @Mock private BookRepository bookRepository;
    @Mock private GenreRepository genreRepository;
    @Mock private AuthorRepository authorRepository;
    @Mock private PhotoRepository photoRepository;
    @Mock private ReaderRepository readerRepository;
    @Mock private IDGeneratorFactory idGeneratorFactory;

    @InjectMocks private BookServiceImpl bookService;

    @Test
    void testCreateBookSuccess() {
        CreateBookRequest request = new CreateBookRequest();
        request.setTitle("Book Title");
        request.setGenre("Fiction");
        request.setAuthors(List.of(1L));
        request.setDescription("Description");
        request.setPhoto(null);
        request.setPhotoURI(null);

        Genre genre = new Genre("1", "Fiction");
        Author author = new Author("João", "teste", "photo1.jpg");

        when(idGeneratorFactory.generateIdUsing(anyString(), anyString())).thenReturn("ISBN-1");
        when(bookRepository.findByIsbn("ISBN-1")).thenReturn(Optional.empty());
        when(genreRepository.findByString("Fiction")).thenReturn(Optional.of(genre));
        when(authorRepository.findByAuthorNumber(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Book book = bookService.create(request);

        assertEquals("ISBN-1", book.getIsbn());
        assertEquals("Book Title", book.getTitle().getTitle());
        assertEquals(genre, book.getGenre());
    }

    @Test
    void testCreateBookDuplicateIsbn() {
        CreateBookRequest request = new CreateBookRequest();
        request.setTitle("Book Title");
        request.setGenre("Fiction");
        request.setAuthors(List.of(1L));

        when(idGeneratorFactory.generateIdUsing(anyString(), anyString())).thenReturn("ISBN-1");
        when(bookRepository.findByIsbn("ISBN-1")).thenReturn(Optional.of(new Book()));

        assertThrows(ConflictException.class, () -> bookService.create(request));
    }
}
