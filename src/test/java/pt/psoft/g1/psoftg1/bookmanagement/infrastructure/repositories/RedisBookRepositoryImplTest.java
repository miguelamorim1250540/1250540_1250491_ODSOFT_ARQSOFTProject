package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.redis.BookCache;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.mapper.BookCacheMapper;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.RedisBookRepositoryImpl;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

public class RedisBookRepositoryImplTest {
    @Mock
    private RedisBookRepository redisBookRepository;

    @InjectMocks
    private RedisBookRepositoryImpl repository;

    private Book sampleBook;
    private BookCache sampleCache;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        sampleBook = new Book("id-1", "9782826012092", "Encantos de contar", "Descrição",
                new Genre("Fantasia"), List.of(new Author("João", "Bio", null)), null);

        sampleCache = BookCacheMapper.toBookCache(sampleBook);
    }

    @Test
    void findByIsbn_shouldReturnBook_whenExists() {
        when(redisBookRepository.findById("9782826012092")).thenReturn(Optional.of(sampleCache));

        Optional<Book> result = repository.findByIsbn("9782826012092");

        assertTrue(result.isPresent());
        assertEquals("9782826012092", result.get().getIsbn());
        verify(redisBookRepository).findById("9782826012092");
    }

    @Test
    void findByIsbn_shouldReturnEmpty_whenNotFound() {
        when(redisBookRepository.findById("1234567890")).thenReturn(Optional.empty());

        Optional<Book> result = repository.findByIsbn("1234567890");

        assertFalse(result.isPresent());
        verify(redisBookRepository).findById("1234567890");
    }

    @Test
    void findByGenre_shouldReturnFilteredBooks() {
        when(redisBookRepository.findAll()).thenReturn(List.of(sampleCache));

        List<Book> result = repository.findByGenre("Fantasia");

        assertEquals(1, result.size());
        assertEquals("Fantasia", result.get(0).getGenre().getGenre());
        verify(redisBookRepository).findAll();
    }

    @Test
    void findByTitle_shouldReturnFilteredBooks() {
        when(redisBookRepository.findAll()).thenReturn(List.of(sampleCache));

        List<Book> result = repository.findByTitle("Encantos");

        assertEquals(1, result.size());
        assertTrue(result.get(0).getTitle().toString().contains("Encantos"));
        verify(redisBookRepository).findAll();
    }

    @Test
    void save_shouldCallRedisRepository() {
        repository.save(sampleBook);

        verify(redisBookRepository).save(any(BookCache.class));
    }

    @Test
    void delete_shouldCallRedisRepositoryDeleteById() {
        repository.delete(sampleBook);

        verify(redisBookRepository).deleteById("9782826012092");
    }

    /*@Test
    void findTop5BooksLent_shouldReturnEmptyPage() {
        Page<BookCountDTO> page = repository.findTop5BooksLent(null, Pageable.unpaged());
        assertTrue(page.isEmpty());
    }*/
}
