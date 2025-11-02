package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.RepositoryRedis;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelRedis.BookDataModelRedis;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mappers.BookMapperRedis;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.services.SearchBooksQuery;

public class RedisBookRepositoryImpl implements BookRepository{
    private final RedisBookRepository redisBookRepository;

    public RedisBookRepositoryImpl(RedisBookRepository redisBookRepository){
        this.redisBookRepository = redisBookRepository;
    }


    @Override
    public List<Book> findByGenre(String genre) {
        Iterable<BookDataModelRedis> allBooks = redisBookRepository.findAll();
        List<Book> filtered = new ArrayList<>();

        for (BookDataModelRedis cache : allBooks) {
            if (cache.getGenre() != null && cache.getGenre().toString().equalsIgnoreCase(genre)) {
                filtered.add(BookMapperRedis.toDomain(cache));
            }
        }

        return filtered;
    }

    @Override
    public List<Book> findByTitle(String title) {
        Iterable<BookDataModelRedis> allBooks = redisBookRepository.findAll();
        List<Book> filtered = new ArrayList<>();

        for (BookDataModelRedis cache : allBooks) {
            if (cache.getTitle() != null &&
                cache.getTitle().toLowerCase().contains(title.toLowerCase())) {
                filtered.add(BookMapperRedis.toDomain(cache));
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
        return redisBookRepository.findById(isbn).map(cache -> BookMapperRedis.toDomain(cache));
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
        BookDataModelRedis cache = BookMapperRedis.toDataModel(book);
        redisBookRepository.save(cache);
        return book;
    }

    @Override
    public void delete(Book book) {
        redisBookRepository.deleteById(book.getIsbn());
    }
}
