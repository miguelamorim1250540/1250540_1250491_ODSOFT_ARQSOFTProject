package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.mongodb.BookDocument;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.mapper.BookDocumentMapper;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.MongoDBBookRepositoryImpl;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

public class MongoDBBookRepositoryImplTest {
    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private MongoDBBookRepository mongodbBookRepository;

    @InjectMocks
    private MongoDBBookRepositoryImpl repository;

    private Book sampleBook;
    private BookDocument sampleDoc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sampleBook = new Book("id-1", "9782826012092", "Encantos de contar", "Descrição",
                new Genre("Fantasia"), List.of(new Author("João", "Bio", null)), null);

        sampleDoc = BookDocumentMapper.toBookDocument(sampleBook);
    }

    @Test
    void findByIsbn_shouldReturnBook_whenExists() {
        when(mongoTemplate.findOne(any(Query.class), eq(BookDocument.class))).thenReturn(sampleDoc);

        Optional<Book> result = repository.findByIsbn("9782826012092");

        assertTrue(result.isPresent());
        assertEquals("9782826012092", result.get().getIsbn());
        verify(mongoTemplate).findOne(any(Query.class), eq(BookDocument.class));
    }

    @Test
    void findByIsbn_shouldReturnEmpty_whenNotFound() {
        when(mongoTemplate.findOne(any(Query.class), eq(BookDocument.class))).thenReturn(null);

        Optional<Book> result = repository.findByIsbn("1234567890");

        assertFalse(result.isPresent());
        verify(mongoTemplate).findOne(any(Query.class), eq(BookDocument.class));
    }

    @Test
    void save_shouldCallRepositoryAndReturnBook() {
        when(mongodbBookRepository.save(any(BookDocument.class))).thenReturn(sampleDoc);

        Book saved = repository.save(sampleBook);

        assertNotNull(saved);
        assertEquals(sampleBook.getIsbn(), saved.getIsbn());
        verify(mongodbBookRepository).save(any(BookDocument.class));
    }

    @Test
    void delete_shouldCallRepositoryDeleteById() {
        repository.delete(sampleBook);

        verify(mongodbBookRepository).deleteById("9782826012092");
    }
}
