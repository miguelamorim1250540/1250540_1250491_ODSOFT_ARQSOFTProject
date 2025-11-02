package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.RepositoryMongoDB;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.AuthorDataModelMongoDB;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mappers.AuthorMapperMongoDb;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelMongo.BookDataModelMongo;

@Profile("mongoRedis")
public class MongoDBAuthorRepositoryImpl implements AuthorRepository {

    private final MongoTemplate mongoTemplate;

    public MongoDBAuthorRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Author> findByAuthorNumber(Long authorNumber) {
        if (authorNumber == null) return Optional.empty();
        Query q = new Query(Criteria.where("authorNumber").is(authorNumber.toString()));
        AuthorDataModelMongoDB dm = mongoTemplate.findOne(q, AuthorDataModelMongoDB.class, "authors");
        return Optional.ofNullable(AuthorMapperMongoDb.toDomain(dm));
    }

    @Override
    public List<Author> searchByNameStartsWith(String name) {
        if (name == null || name.isBlank()) return Collections.emptyList();
        Query q = new Query(Criteria.where("name").regex("^" + Pattern.quote(name), "i"));
        return mongoTemplate.find(q, AuthorDataModelMongoDB.class, "authors")
                .stream().map(AuthorMapperMongoDb::toDomain).toList();
    }

    @Override
    public List<Author> searchByNameName(String name) {
        if (name == null || name.isBlank()) return Collections.emptyList();
        Query q = new Query(Criteria.where("name").is(name));
        return mongoTemplate.find(q, AuthorDataModelMongoDB.class, "authors")
                .stream().map(AuthorMapperMongoDb::toDomain).toList();
    }

    @Override
    public Author save(Author author) {
        AuthorDataModelMongoDB dm = AuthorMapperMongoDb.toDataModel(author);
        AuthorDataModelMongoDB saved = mongoTemplate.save(dm, "authors");
        return AuthorMapperMongoDb.toDomain(saved);
    }

    @Override
    public Iterable<Author> findAll() {
        return mongoTemplate.findAll(AuthorDataModelMongoDB.class, "authors")
                .stream().map(AuthorMapperMongoDb::toDomain).toList();

    }

    @Override
    public Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageableRules) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.lookup("books", "authorNumber", "authors.authorNumber", "book"),
                Aggregation.unwind("book"),
                Aggregation.lookup("lendings", "book.isbn", "bookId", "lending"),
                Aggregation.unwind("lending"),
                Aggregation.group("authorNumber", "name").count().as("lendingsCount"),
                //Aggregation.sort(Sort.Direction.DESC, "lendingsCount"),
                Aggregation.skip((long) pageableRules.getPageNumber() * pageableRules.getPageSize()),
                Aggregation.limit(pageableRules.getPageSize())
        );

        //AggregationResults<Document> results = mongoTemplate.aggregate(agg, "authors", Document.class);

        /*List<AuthorLendingView> list = results.getMappedResults().stream()
                .map(doc -> {
                    Document id = doc.get("_id", Document.class);
                    String authorNumber = id.getString("authorNumber");
                    String name = id.getString("name");
                    long count = doc.getLong("lendingsCount");
                    return new AuthorLendingView(authorNumber, name, count);
                }).toList();*/

        //return new PageImpl<>(list, pageableRules, list.size());
        return null;
    }

    @Override
    public void delete(Author author) {
        if (author == null) return;
        Query q = new Query(Criteria.where("authorNumber").is(author.getAuthorNumber()));
        mongoTemplate.remove(q, AuthorDataModelMongoDB.class, "authors");
    }

    @Override
    public List<Author> findCoAuthorsByAuthorNumber(Long authorNumber) {
        if (authorNumber == null) return Collections.emptyList();

        // Encontrar todos os livros que esse autor escreveu
        Query bookQuery = new Query(Criteria.where("authors.authorNumber").is(authorNumber.toString()));
        List<BookDataModelMongo> books = mongoTemplate.find(bookQuery, BookDataModelMongo.class, "books");

        // Coautores diferentes do authorNumber
        Set<String> coAuthorNumbers = books.stream()
                .flatMap(book -> book.getAuthors().stream())
                .map(AuthorDataModelMongoDB::getAuthorNumber)
                .filter(num -> !num.equals(authorNumber.toString()))
                .collect(Collectors.toSet());

        if (coAuthorNumbers.isEmpty()) return Collections.emptyList();

        // Buscar os coautores no Mongo
        Query coAuthorQuery = new Query(Criteria.where("authorNumber").in(coAuthorNumbers));
        return mongoTemplate.find(coAuthorQuery, AuthorDataModelMongoDB.class, "authors")
                .stream().map(AuthorMapperMongoDb::toDomain).toList();
    }
    
}
