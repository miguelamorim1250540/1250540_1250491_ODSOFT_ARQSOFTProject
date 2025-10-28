package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.redis.BookCache;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.mapper.BookCacheMapper;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

public class BookCacheMapperTest {
    @Test
    void bookToCache_andBack_shouldBeConsistent() {
        // Arrange
        Genre genre = new Genre("Fantasia");
        Author author = new Author("João", "Bio", null);
        Book book = new Book("id-1", "9782826012092", "Encantos de contar", "Descrição", genre, List.of(author), null);

        // Act
        BookCache cache = BookCacheMapper.toBookCache(book);
        Book result = BookCacheMapper.toDomain(cache);

        // Assert
        assertEquals(book.getIsbn(), result.getIsbn());
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getGenre().getGenre(), result.getGenre().getGenre());
        assertEquals(book.getAuthors().size(), result.getAuthors().size());
    }
}
