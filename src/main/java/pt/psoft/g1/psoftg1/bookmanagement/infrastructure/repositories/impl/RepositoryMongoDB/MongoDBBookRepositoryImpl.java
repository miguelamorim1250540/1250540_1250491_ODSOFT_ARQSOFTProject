package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.RepositoryMongoDB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelMongo.BookDataModelMongo;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mappers.BookMapperMongo;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.services.SearchBooksQuery;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels.LendingDataModelMongo;

@Profile("mongoRedis")
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

        return mongoTemplate.find(query, BookDataModelMongo.class).stream()
            .map(doc -> BookMapperMongo.toDomain(doc)).toList();
    }

    @Override
    public List<Book> findByTitle(String title) {
        Query query = new Query(Criteria.where("title.title").regex(title, "i"));

        return mongoTemplate.find(query, BookDataModelMongo.class).stream()
            .map(doc -> BookMapperMongo.toDomain(doc)).toList();
    }

    @Override
    public List<Book> findByAuthorName(String authorName) {
        Query query = new Query(Criteria.where("author.name").regex(authorName, "i"));

        return mongoTemplate.find(query, BookDataModelMongo.class).stream()
            .map(doc -> BookMapperMongo.toDomain(doc)).toList();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        Query query = new Query(Criteria.where("isbn.isbn").is(isbn));

        return Optional.ofNullable(BookMapperMongo.toDomain(mongoTemplate.findOne(query, BookDataModelMongo.class)));
    }

    @Override
    public Page<BookCountDTO> findTop5BooksLent(LocalDate oneYearAgo, Pageable pageable) {
        MatchOperation match = Aggregation.match(Criteria.where("lendingDate").gte(oneYearAgo));
        GroupOperation group = Aggregation.group("bookIsbn").count().as("lendingCount");
        SortOperation sort = Aggregation.sort(Sort.Direction.DESC, "lendingCount");
        LimitOperation limit = Aggregation.limit(5);

        Aggregation aggregation = Aggregation.newAggregation(match, group, sort, limit);
        AggregationResults<LendingDataModelMongo> results = mongoTemplate.aggregate(aggregation, "lendings", LendingDataModelMongo.class);

        List<BookCountDTO> dtos = results.getMappedResults().stream()
                .map(doc -> {
                    String isbn = doc.getBookId();
                    long count = doc.getFineValuePerDayInCents();
                    Optional<Book> book = findByIsbn(isbn);
                    return book.map(value -> new BookCountDTO(value, count)).orElse(null);
                })
                .filter(Objects::nonNull)
                .toList();

        return new PageImpl<>(dtos, pageable, dtos.size());
    }

    @Override
    public List<Book> findBooksByAuthorNumber(Long authorNumber) {
        Query query = new Query(Criteria.where("authors.authorNumber").is(String.valueOf(authorNumber)));
        return mongoTemplate.find(query, BookDataModelMongo.class).stream()
                .map(BookMapperMongo::toDomain)
                .toList();
    }

    @Override
    public List<Book> searchBooks(pt.psoft.g1.psoftg1.shared.services.Page page, SearchBooksQuery query) {
        Criteria criteria = new Criteria();

        List<Criteria> filters = new ArrayList<>();
        if (query.getTitle() != null && !query.getTitle().isBlank())
            filters.add(Criteria.where("title").regex(query.getTitle(), "i"));
        if (query.getGenre() != null && !query.getGenre().isBlank())
            filters.add(Criteria.where("genre.genre").regex(query.getGenre(), "i"));
        if (query.getAuthorName() != null && !query.getAuthorName().isBlank())
            filters.add(Criteria.where("authors.name").regex(query.getAuthorName(), "i"));

        if (!filters.isEmpty())
            criteria = new Criteria().andOperator(filters.toArray(new Criteria[0]));

        Query mongoQuery = new Query(criteria);
        mongoQuery.skip((long) (page.getNumber() - 1) * page.getLimit());
        mongoQuery.limit(page.getLimit());

        return mongoTemplate.find(mongoQuery, BookDataModelMongo.class).stream()
                .map(BookMapperMongo::toDomain)
                .toList();
    }

    @Override
    public Book save(Book book) {
        BookDataModelMongo doc = BookMapperMongo.toDataModel(book);
        BookDataModelMongo docSaved = mongodbBookRepository.save(doc);
        
        return BookMapperMongo.toDomain(docSaved);
    }

    @Override
    public void delete(Book book) {
        mongodbBookRepository.deleteById(book.getIsbn());
    }
}
