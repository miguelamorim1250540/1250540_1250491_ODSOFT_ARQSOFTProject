package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.RepositoryMongoDB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelMongo.BookDataModelMongo;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.DataModels.GenreDataModelMongo;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.mappers.GenreMapperMongo;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsDTO;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsPerMonthDTO;

@Profile("mongoRedis")
public class MongoDBGenreRepositoryImpl implements GenreRepository {

    private final MongoTemplate mongoTemplate;

    public MongoDBGenreRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Iterable<Genre> findAll() {
        List<GenreDataModelMongo> dataModels = mongoTemplate.findAll(GenreDataModelMongo.class);
        return dataModels.stream().map(dm -> GenreMapperMongo.toDomain(dm)).toList();
    }

    @Override
    public Optional<Genre> findByString(String genreName) {
        if (genreName == null || genreName.isBlank()) return Optional.empty();
        Query q = new Query(Criteria.where("genre").is(genreName));
        GenreDataModelMongo dm = mongoTemplate.findOne(q, GenreDataModelMongo.class);
        return Optional.ofNullable(GenreMapperMongo.toDomain(dm));
    }

    @Override
    public Genre save(Genre genre) {
        GenreDataModelMongo dm = GenreMapperMongo.toDataModel(genre);
        GenreDataModelMongo saved = mongoTemplate.save(dm);
        return GenreMapperMongo.toDomain(saved);
    }

    @Override
    public Page<GenreBookCountDTO> findTop5GenreByBookCount(Pageable pageable) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.group("genre.genre").count().as("bookCount"),
                //Aggregation.sort(Sort.Direction.DESC, "bookCount"),
                Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize()),
                Aggregation.limit(pageable.getPageSize())
        );

        AggregationResults<BookDataModelMongo> results = mongoTemplate.aggregate(agg, "books", BookDataModelMongo.class);
        List<GenreBookCountDTO> list = results.getMappedResults().stream()
                .map(doc -> new GenreBookCountDTO(
                        //new Genre(doc.getString("_id")),
                        //doc.getLong("bookCount")
                ))
                .toList();

        return new PageImpl<>(list, pageable, list.size());
    }

    @Override
    public List<GenreLendingsDTO> getAverageLendingsInMonth(LocalDate month,
            pt.psoft.g1.psoftg1.shared.services.Page page) {
        LocalDate start = month.withDayOfMonth(1);
        LocalDate end = month.withDayOfMonth(month.lengthOfMonth());

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("startDate").gte(start).lte(end)),
                Aggregation.lookup("books", "bookId", "isbn", "book"),
                Aggregation.unwind("book"),
                Aggregation.group("book.genre.genre").count().as("lendingsCount"),
                //Aggregation.sort(Sort.Direction.DESC, "lendingsCount"),
                Aggregation.skip((long) (page.getNumber() - 1) * page.getLimit()),
                Aggregation.limit(page.getLimit())
        );

        /*AggregationResults<LendingDataModelMongo> results = mongoTemplate.aggregate(agg, "lendings", LendingDataModelMongo.class);
        return results.getMappedResults().stream()
                .map(doc -> new GenreLendingsDTO(doc.getString("_id"), doc.getLong("lendingsCount")))
                .toList();*/
                return null;
    }

    @Override
    public List<GenreLendingsPerMonthDTO> getLendingsPerMonthLastYearByGenre() {
        LocalDate today = LocalDate.now();
        LocalDate oneYearAgo = today.minusYears(1).withDayOfMonth(1);

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("startDate").gte(oneYearAgo)),
                Aggregation.project()
                        .andExpression("year(startDate)").as("year")
                        .andExpression("month(startDate)").as("month")
                        .and("bookId").as("bookId"),
                Aggregation.lookup("books", "bookId", "isbn", "book"),
                Aggregation.unwind("book"),
                Aggregation.group("year", "month", "book.genre.genre").count().as("count")
                //Aggregation.sort(Sort.Direction.ASC, "_id.year", "_id.month")
        );

        //AggregationResults<Document> results = mongoTemplate.aggregate(agg, "lendings", Document.class);

        //Map<String, Map<Integer, List<GenreLendingsDTO>>> grouped = new HashMap<>();
        /*for (Document doc : results.getMappedResults()) {
            Document id = doc.get("_id", Document.class);
            int year = id.getInteger("year");
            int month = id.getInteger("month");
            String genre = id.getString("book.genre.genre");
            long count = doc.getLong("count");

            grouped
                .computeIfAbsent(year + "-" + month, k -> new HashMap<>())
                .computeIfAbsent(month, k -> new ArrayList<>())
                .add(new GenreLendingsDTO(genre, count));
        }*/

        List<GenreLendingsPerMonthDTO> output = new ArrayList<>();
        /*grouped.forEach((key, monthMap) -> {
            monthMap.forEach((month, values) -> {
                String[] parts = key.split("-");
                int year = Integer.parseInt(parts[0]);
                output.add(new GenreLendingsPerMonthDTO(year, month, values));
            });
        });*/

        return output;
    }

    @Override
    public List<GenreLendingsPerMonthDTO> getLendingsAverageDurationPerMonth(LocalDate startDate, LocalDate endDate) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("startDate").gte(startDate).lte(endDate).and("returnedDate").ne(null)),
                Aggregation.project()
                        .andExpression("year(startDate)").as("year")
                        .andExpression("month(startDate)").as("month")
                        .andExpression("returnedDate - startDate").as("durationDays")
                        .and("bookId").as("bookId"),
                Aggregation.lookup("books", "bookId", "isbn", "book"),
                Aggregation.unwind("book"),
                Aggregation.group("year", "month", "book.genre.genre").avg("durationDays").as("avgDuration")
                //Aggregation.sort(Sort.Direction.ASC, "_id.year", "_id.month")
        );

        //AggregationResults<Document> results = mongoTemplate.aggregate(agg, "lendings", Document.class);

        //Map<String, Map<Integer, List<GenreLendingsDTO>>> grouped = new HashMap<>();
        /*for (Document doc : results.getMappedResults()) {
            Document id = doc.get("_id", Document.class);
            int year = id.getInteger("year");
            int month = id.getInteger("month");
            String genre = id.getString("book.genre.genre");
            double avg = doc.getDouble("avgDuration");

            grouped
                .computeIfAbsent(year + "-" + month, k -> new HashMap<>())
                .computeIfAbsent(month, k -> new ArrayList<>())
                .add(new GenreLendingsDTO(genre, avg));
        }*/

        List<GenreLendingsPerMonthDTO> output = new ArrayList<>();
        /*grouped.forEach((key, monthMap) -> {
            monthMap.forEach((month, values) -> {
                String[] parts = key.split("-");
                int year = Integer.parseInt(parts[0]);
                output.add(new GenreLendingsPerMonthDTO(year, month, values));
            });
        });*/

        return output;
    }

    @Override
    public void delete(Genre genre) {
        if (genre == null) return;
        Query q = new Query(Criteria.where("genre").is(genre.toString()));
        mongoTemplate.remove(q, GenreDataModelMongo.class);
    }
    
}
