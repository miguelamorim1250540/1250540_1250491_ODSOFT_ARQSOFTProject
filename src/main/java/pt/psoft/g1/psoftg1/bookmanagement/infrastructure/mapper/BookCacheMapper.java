package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.mapper;

import java.util.List;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.redis.BookCache;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.redis.DescriptionCache;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.redis.IsbnCache;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.redis.TitleCache;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;

public class BookCacheMapper {

    
    public static BookCache toBookCache(Book book) {
        /* TODO: AuthorCache and GenreCache
         List<String> authorsIdList = book.getAuthors().stream()
            .map(author -> String.valueOf(author.getId()))
            .toList();
        */

        return new BookCache(
            book.getId(), 
            new IsbnCache(book.getIsbn()),
            new TitleCache(book.getTitle().toString()),
            new DescriptionCache(book.getDescription()),
            book.getGenre(),
            null,
            null);
    }

    public static Book toDomain (BookCache bookCache) {
        return new Book(
            bookCache.getId(),
            bookCache.getIsbn().getIsbn(),
            bookCache.getTitle().getTitle(),
            bookCache.getDescription().getDescription(),
            bookCache.getGenre(),
            null,
            null);
    }
    
}