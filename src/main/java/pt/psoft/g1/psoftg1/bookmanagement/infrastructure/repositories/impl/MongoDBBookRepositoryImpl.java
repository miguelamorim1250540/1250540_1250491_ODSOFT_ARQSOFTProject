package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.query.Param;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.mongodb.BookDocument;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.mapper.BookDocumentMapper;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.MongoDBBookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.services.SearchBooksQuery;


public class MongoDBBookRepositoryImpl implements BookRepository{
    private final MongoDBBookRepository mongodbBookRepository;

    public MongoDBBookRepositoryImpl(MongoDBBookRepository mongodbBookRepository, MongoTemplate mongoTemplate) {
        this.mongodbBookRepository = mongodbBookRepository;
    }

    @Override
    public List<Book> findByGenre(@Param("genre") String genre) {
        return null;
    }

    @Override
    public List<Book> findByTitle(@Param("title") String title) {
        return null;
    }

    @Override
    public List<Book> findByAuthorName(@Param("authorName") String authorName) {
        return null;
    }

    @Override
    public Optional<Book> findByIsbn(@Param("isbn") String isbn) {
        return null;
    }

    @Override
    public Page<BookCountDTO> findTop5BooksLent(@Param("oneYearAgo") LocalDate oneYearAgo, Pageable pageable) {
        return null;
    }

    @Override
    public List<Book> findBooksByAuthorNumber(Long authorNumber) {
        return null;
    }

    @Override
    public List<Book> searchBooks(pt.psoft.g1.psoftg1.shared.services.Page page, SearchBooksQuery query) {
        return null;
    }

    @Override
    public Book save(Book book) {
        BookDocument doc = BookDocumentMapper.toBookDocument(book);

        return BookDocumentMapper.toDomain(mongodbBookRepository.save(doc), book.getGenre(), book.getAuthors());
    }

    @Override
    public void delete(Book book) {
        mongodbBookRepository.deleteById(book.getIsbn());
    }
}
