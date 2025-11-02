package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.RepositoryMongoDB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import jakarta.validation.constraints.NotNull;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels.LendingDataModelMongo;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.DataModels.ReaderDetailsDataModelMongo;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.mappers.ReaderDetailsMapperMongo;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderBookCountDTO;
import pt.psoft.g1.psoftg1.readermanagement.services.SearchReadersQuery;

@Profile("mongoRedis")
public class MongoDBReaderRepositoryImpl implements ReaderRepository{

    private final MongoTemplate mongoTemplate;
    private final ReaderDetailsMapperMongo mapper;

    public MongoDBReaderRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.mapper = new ReaderDetailsMapperMongo();
    }

    @Override
    public Optional<ReaderDetails> findByReaderNumber(@NotNull String readerNumber) {
        Query q = new Query(Criteria.where("readerNumber").is(readerNumber));
        ReaderDetailsDataModelMongo dm = mongoTemplate.findOne(q, ReaderDetailsDataModelMongo.class);
        return Optional.ofNullable(mapper.toDomain(dm));
    }

    @Override
    public List<ReaderDetails> findByPhoneNumber(@NotNull String phoneNumber) {
        Query q = new Query(Criteria.where("phoneNumber").is(phoneNumber));
        List<ReaderDetailsDataModelMongo> results = mongoTemplate.find(q, ReaderDetailsDataModelMongo.class);
        return results.stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<ReaderDetails> findByUsername(@NotNull String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUsername'");
    }

    @Override
    public Optional<ReaderDetails> findByUserId(@NotNull Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByUserId'");
    }

    @Override
    public int getCountFromCurrentYear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCountFromCurrentYear'");
    }

    @Override
    public ReaderDetails save(ReaderDetails readerDetails) {
        if (readerDetails == null) return null;
        ReaderDetailsDataModelMongo dm = new ReaderDetailsDataModelMongo(readerDetails);
        mongoTemplate.save(dm);
        return mapper.toDomain(dm);
    }

    @Override
    public Iterable<ReaderDetails> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public Page<ReaderDetails> findTopReaders(Pageable pageable) {
        GroupOperation group = Aggregation.group("readerNumber").count().as("lendingCount");
        SortOperation sort = Aggregation.sort(Sort.by(Sort.Direction.DESC, "lendingCount"));
        
        LookupOperation lookup = LookupOperation.newLookup()
                .from("readerDetails")             
                .localField("_id")                
                .foreignField("readerNumber")     
                .as("readerDetails");

        Aggregation agg = Aggregation.newAggregation(group, sort, lookup,
                Aggregation.unwind("readerDetails"),
                Aggregation.project()
                        .and("readerDetails").as("readerDetails")
                        .and("lendingCount").as("lendingCount"));

        AggregationResults<LendingDataModelMongo> results = mongoTemplate.aggregate(agg, "lendings", LendingDataModelMongo.class);
        List<LendingDataModelMongo> mapped = results.getMappedResults();

        int total = mapped.size();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), mapped.size());
        List<ReaderDetails> content = new ArrayList<>();
        /*if (start < end) {
            for (int i = start; i < end; i++) {
                LendingDataModelMongo doc = mapped.get(i);
                LendingDataModelMongo rd = (LendingDataModelMongo) doc.get("readerDetails");
                // convert Document -> ReaderDetailsDataModelMongo (simple way via mapping fields)
                ReaderDetailsDataModelMongo rdDm = documentToReaderDetailsDataModel(rd);
                ReaderDetails domain = mapper.toDomain(rdDm);
                content.add(domain);
            }
        }*/

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<ReaderBookCountDTO> findTopByGenre(Pageable pageable, String genre, LocalDate startDate,
            LocalDate endDate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findTopByGenre'");
    }

    @Override
    public void delete(ReaderDetails readerDetails) {
        if (readerDetails == null) return;
        Query q = new Query(Criteria.where("readerNumber").is(readerDetails.getReaderNumber()));
        mongoTemplate.remove(q, ReaderDetailsDataModelMongo.class);
    }

    @Override
    public List<ReaderDetails> searchReaderDetails(pt.psoft.g1.psoftg1.shared.services.Page page,
            SearchReadersQuery query) {
        List<Criteria> criterias = new ArrayList<>();

        if (query.getPhoneNumber() != null && !query.getPhoneNumber().isBlank()) {
            criterias.add(Criteria.where("phoneNumber").regex(query.getPhoneNumber(), "i"));
        }

        // nome/email não existem no data model fornecido - não posso filtrar por eles sem alteração de schema
        Criteria finalCrit = new Criteria();
        if (!criterias.isEmpty()) {
            finalCrit = new Criteria().andOperator(criterias.toArray(new Criteria[0]));
        }

        Query q = new Query(finalCrit);
        q.skip((long) (page.getNumber() - 1) * page.getLimit());
        q.limit(page.getLimit());

        List<ReaderDetailsDataModelMongo> results = mongoTemplate.find(q, ReaderDetailsDataModelMongo.class);
        return results.stream().map(mapper::toDomain).toList();
    }
    
}
