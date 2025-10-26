package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.mongodb.BookDocument;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.mapper.BookDocumentMapper;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.MongoDBBookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.services.SearchBooksQuery;

@Repository
@Profile("mongo-redis")
public class MongoDBBookRepositoryImpl implements BookRepository{
    private final MongoTemplate mongoTemplate;
    private final MongoDBBookRepository mongodbBookRepository;

    public MongoDBBookRepositoryImpl(MongoDBBookRepository mongodbBookRepository, MongoTemplate mongoTemplate) {
        this.mongodbBookRepository = mongodbBookRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Book> findByGenre(String genre) {
        Query query = new Query(Criteria.where("genre.genre").regex(genre, "i"));

        return mongoTemplate.find(query, BookDocument.class).stream()
            .map(doc -> BookDocumentMapper.toDomain(doc)).toList();
    }

    @Override
    public List<Book> findByTitle(String title) {
        Query query = new Query(Criteria.where("title.title").regex(title, "i"));

        return mongoTemplate.find(query, BookDocument.class).stream()
            .map(doc -> BookDocumentMapper.toDomain(doc)).toList();
    }

    @Override
    public List<Book> findByAuthorName(String authorName) {
        Query query = new Query(Criteria.where("author.name").regex(authorName, "i"));

        return mongoTemplate.find(query, BookDocument.class).stream()
            .map(doc -> BookDocumentMapper.toDomain(doc)).toList();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        Query query = new Query(Criteria.where("isbn.isbn").is(isbn));

        return Optional.ofNullable(BookDocumentMapper.toDomain(mongoTemplate.findOne(query, BookDocument.class)));
    }

    @Override
    public Page<BookCountDTO> findTop5BooksLent(LocalDate oneYearAgo, Pageable pageable) {
        //TODO: Fazer apos os lendings
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
        BookDocument docSaved = mongodbBookRepository.save(doc);
        
        return BookDocumentMapper.toDomain(docSaved);
    }

    @Override
    public void delete(Book book) {
        mongodbBookRepository.deleteById(book.getIsbn());
    }
}
