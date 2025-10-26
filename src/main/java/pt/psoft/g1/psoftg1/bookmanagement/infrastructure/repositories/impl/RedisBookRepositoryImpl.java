package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.redis.BookCache;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.mapper.BookCacheMapper;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.RedisBookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.services.SearchBooksQuery;

public class RedisBookRepositoryImpl implements BookRepository {
    private final RedisBookRepository redisBookRepository;

    public RedisBookRepositoryImpl(RedisBookRepository redisBookRepository){
        this.redisBookRepository = redisBookRepository;
    }


    @Override
    public List<Book> findByGenre(String genre) {
        Iterable<BookCache> allBooks = redisBookRepository.findAll();
        List<Book> filtered = new ArrayList<>();

        for (BookCache cache : allBooks) {
            if (cache.getGenre() != null && cache.getGenre().toString().equalsIgnoreCase(genre)) {
                filtered.add(BookCacheMapper.toDomain(cache));
            }
        }

        return filtered;
    }

    @Override
    public List<Book> findByTitle(String title) {
        Iterable<BookCache> allBooks = redisBookRepository.findAll();
        List<Book> filtered = new ArrayList<>();

        for (BookCache cache : allBooks) {
            if (cache.getTitle() != null &&
                cache.getTitle().getTitle().toLowerCase().contains(title.toLowerCase())) {
                filtered.add(BookCacheMapper.toDomain(cache));
            }
        }

        return filtered;
    }

    @Override
    public List<Book> findByAuthorName(String authorName) {
        // TODO: AuthorCache
        throw new UnsupportedOperationException("Unimplemented method 'findByAuthorName'");
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return redisBookRepository.findById(isbn).map(cache -> BookCacheMapper.toDomain(cache));
    }

    @Override
    public Page<BookCountDTO> findTop5BooksLent(LocalDate oneYearAgo, Pageable pageable) {
        return Page.empty(pageable);
    }

    @Override
    public List<Book> findBooksByAuthorNumber(Long authorNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findBooksByAuthorNumber'");
    }

    @Override
    public List<Book> searchBooks(pt.psoft.g1.psoftg1.shared.services.Page page, SearchBooksQuery query) {
        return null;
    }

    @Override
    public Book save(Book book) {
        BookCache cache = BookCacheMapper.toBookCache(book);
        redisBookRepository.save(cache);
        return book;
    }

    @Override
    public void delete(Book book) {
        redisBookRepository.deleteById(book.getIsbn());
    }
    
}
