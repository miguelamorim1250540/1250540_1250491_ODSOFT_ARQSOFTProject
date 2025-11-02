package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.RepositoryMongoDB;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels.LendingDataModelMongo;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.mappers.LendingMapperMongo;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.LendingRepository;
import pt.psoft.g1.psoftg1.shared.services.Page;

@Profile("mongoRedis")
public class MongoDBLendingRepositoryImpl implements LendingRepository{
    private final MongoTemplate mongoTemplate;
    private final LendingMapperMongo lendingMapper;

    public MongoDBLendingRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.lendingMapper = new LendingMapperMongo();
    }

    @Override
    public Optional<Lending> findByLendingNumber(String lendingNumber) {
        if (lendingNumber == null) return Optional.empty();
        Query q = new Query(Criteria.where("lendingNumber").is(lendingNumber));
        LendingDataModelMongo dm = mongoTemplate.findOne(q, LendingDataModelMongo.class);
        if (dm == null) return Optional.empty();
        
        Lending domain = LendingMapperMongo.toDomain(dm);
        return Optional.ofNullable(domain);
    }

    @Override
    public List<Lending> listByReaderNumberAndIsbn(String readerNumber, String isbn) {
        Query q = new Query(Criteria.where("readerId").is(readerNumber).and("bookId").is(isbn));
        List<LendingDataModelMongo> dms = mongoTemplate.find(q, LendingDataModelMongo.class);
        return dms.stream().map(lendingMongo -> LendingMapperMongo.toDomain(lendingMongo)).toList();
    }

    @Override
    public int getCountFromCurrentYear() {
        LocalDate startOfYear = LocalDate.of(Year.now().getValue(), 1, 1);
        LocalDate startOfNextYear = startOfYear.plusYears(1);
        Query q = new Query(Criteria.where("startDate").gte(startOfYear).lt(startOfNextYear));
        return (int) mongoTemplate.count(q, LendingDataModelMongo.class);
    }

    @Override
    public List<Lending> listOutstandingByReaderNumber(String readerNumber) {
        Query q = new Query(Criteria.where("readerId").is(readerNumber).and("returnedDate").is(null));
        List<LendingDataModelMongo> dms = mongoTemplate.find(q, LendingDataModelMongo.class);
        return dms.stream().map(lendingMongo -> LendingMapperMongo.toDomain(lendingMongo)).toList();
    }

    @Override
    public Double getAverageDuration() {
        Query q = new Query(Criteria.where("returnedDate").ne(null));
        List<LendingDataModelMongo> dms = mongoTemplate.find(q, LendingDataModelMongo.class);
        if (dms.isEmpty()) return 0.0;
        double sum = 0;
        int count = 0;
        for (LendingDataModelMongo dm : dms) {
            if (dm.getStartDate() != null && dm.getReturnedDate() != null) {
                long days = ChronoUnit.DAYS.between(dm.getStartDate(), dm.getReturnedDate());
                sum += days;
                count++;
            }
        }
        return count == 0 ? 0.0 : sum / count;
    }

    @Override
    public Double getAvgLendingDurationByIsbn(String isbn) {
        Query q = new Query(Criteria.where("returnedDate").ne(null).and("bookId").is(isbn));
        List<LendingDataModelMongo> dms = mongoTemplate.find(q, LendingDataModelMongo.class);
        if (dms.isEmpty()) return 0.0;
        double sum = 0;
        int count = 0;
        for (LendingDataModelMongo dm : dms) {
            if (dm.getStartDate() != null && dm.getReturnedDate() != null) {
                long days = ChronoUnit.DAYS.between(dm.getStartDate(), dm.getReturnedDate());
                sum += days;
                count++;
            }
        }
        return count == 0 ? 0.0 : sum / count;
    }

    @Override
    public List<Lending> getOverdue(Page page) {
        LocalDate today = LocalDate.now();
        Query q = new Query(Criteria.where("returnedDate").is(null).and("limitDate").lt(today));
        int skip = (page.getNumber() - 1) * page.getLimit();
        q.skip(skip).limit(page.getLimit());
        List<LendingDataModelMongo> dms = mongoTemplate.find(q, LendingDataModelMongo.class);
        return dms.stream().map(lendingMongo -> LendingMapperMongo.toDomain(lendingMongo)).toList();
    }

    @Override
    public List<Lending> searchLendings(Page page, String readerNumber, String isbn, Boolean returned,
            LocalDate startDate, LocalDate endDate) {
        List<Criteria> criterias = new ArrayList<>();
        if (readerNumber != null && !readerNumber.isBlank()) criterias.add(Criteria.where("readerId").is(readerNumber));
        if (isbn != null && !isbn.isBlank()) criterias.add(Criteria.where("bookId").is(isbn));
        if (returned != null) {
            if (returned) criterias.add(Criteria.where("returnedDate").ne(null));
            else criterias.add(Criteria.where("returnedDate").is(null));
        }
        if (startDate != null && endDate != null) {
            criterias.add(Criteria.where("startDate").gte(startDate).lte(endDate));
        } else if (startDate != null) {
            criterias.add(Criteria.where("startDate").gte(startDate));
        } else if (endDate != null) {
            criterias.add(Criteria.where("startDate").lte(endDate));
        }

        Criteria combined = new Criteria();
        if (!criterias.isEmpty()) combined = new Criteria().andOperator(criterias.toArray(new Criteria[0]));

        Query q = new Query(combined);
        q.skip((page.getNumber() - 1L) * page.getLimit()).limit(page.getLimit());
        List<LendingDataModelMongo> dms = mongoTemplate.find(q, LendingDataModelMongo.class);
        return dms.stream().map(lendingMongo -> LendingMapperMongo.toDomain(lendingMongo)).toList();
    }

    @Override
    public Lending save(Lending lending) {
        LendingDataModelMongo dm = lendingMapper.toDataModel(lending);
        mongoTemplate.save(dm);
        return lending;
    }

    @Override
    public void delete(Lending lending) {
        if (lending == null) return;
        Query q = new Query(Criteria.where("lendingNumber").is(lending.getLendingNumber().toString()));
        mongoTemplate.remove(q, LendingDataModelMongo.class);
    }
    
}
