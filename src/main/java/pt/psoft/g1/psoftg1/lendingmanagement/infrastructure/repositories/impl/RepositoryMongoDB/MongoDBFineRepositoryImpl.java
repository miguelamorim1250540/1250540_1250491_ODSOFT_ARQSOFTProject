package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.RepositoryMongoDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels.FineDataModelMongo;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels.LendingDataModelMongo;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.mappers.FineMapperMongo;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.mappers.LendingMapperMongo;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.FineRepository;

@Profile("mongoRedis")
public class MongoDBFineRepositoryImpl implements FineRepository{

    private final MongoTemplate mongoTemplate;
    private final LendingMapperMongo lendingMapper;

    public MongoDBFineRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.lendingMapper = new LendingMapperMongo();
    }

    @Override
    public Optional<Fine> findByLendingNumber(String lendingNumber) {
        if (lendingNumber == null) return Optional.empty();

        Query q = new Query(Criteria.where("lendingNumber").is(lendingNumber));
        FineDataModelMongo fineDm = mongoTemplate.findOne(q, FineDataModelMongo.class);
        if (fineDm == null) return Optional.empty();

        Query lendQ = new Query(Criteria.where("lendingNumber").is(lendingNumber));
        LendingDataModelMongo lendingDm = mongoTemplate.findOne(lendQ, LendingDataModelMongo.class);

        if (lendingDm == null) {
            return Optional.empty();
        }

        Lending lending = LendingMapperMongo.toDomain(lendingDm);

        try {
            Fine fine = new Fine(lending);
            return Optional.of(fine);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Fine> findAll() {
        List<FineDataModelMongo> all = mongoTemplate.findAll(FineDataModelMongo.class);
        List<Fine> result = new ArrayList<>();
        for (FineDataModelMongo dm : all) {
            if (dm.getLendingNumber() == null) continue;
            // try to fetch lending
            Query lendQ = new Query(Criteria.where("lendingNumber").is(dm.getLendingNumber()));
            LendingDataModelMongo lendingDm = mongoTemplate.findOne(lendQ, LendingDataModelMongo.class);
            if (lendingDm == null) continue;
            Lending lending = LendingMapperMongo.toDomain(lendingDm);
            try {
                Fine fine = new Fine(lending);
                result.add(fine);
            } catch (Exception ex) {
                // 
            }
        }
        return result;
    }

    @Override
    public Fine save(Fine fine) {
        if (fine == null) return null;

        FineDataModelMongo dm = FineMapperMongo.toDataModel(fine);
        mongoTemplate.save(dm);
        return fine;
    }
    
}
