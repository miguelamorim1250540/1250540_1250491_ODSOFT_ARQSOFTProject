package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.RepositoryMongoDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import pt.psoft.g1.psoftg1.shared.services.Page;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.DataModel.UserDataModelMongo;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.mappers.UserMapperMongo;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;
import pt.psoft.g1.psoftg1.usermanagement.services.SearchUsersQuery;

@Profile("mongoRedis")
public class MongoDBUserRepositoryImpl implements UserRepository {
    private final MongoTemplate mongoTemplate;
    private final MongoDBUserRepository mongoDBUserRepository;

    public MongoDBUserRepositoryImpl(MongoDBUserRepository mongoDBUserRepository, MongoTemplate mongoTemplate) {
        this.mongoDBUserRepository = mongoDBUserRepository;
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        List<UserDataModelMongo> docs = new ArrayList<>();
        entities.forEach(user -> docs.add(UserMapperMongo.toDataModel(user)));
        mongoTemplate.insertAll(docs);
        return StreamSupport.stream(entities.spliterator(), false).toList();
    }

    @Override
    public <S extends User> S save(S entity) {
        UserDataModelMongo dataModel = UserMapperMongo.toDataModel(entity);
        UserDataModelMongo saved = mongoDBUserRepository.save(dataModel);
        return (S) UserMapperMongo.toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long objectId) {
        Query query = new Query(Criteria.where("id").is(objectId.toString()));
        UserDataModelMongo found = mongoTemplate.findOne(query, UserDataModelMongo.class);
        return Optional.ofNullable(UserMapperMongo.toDomain(found));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        UserDataModelMongo found = mongoTemplate.findOne(query, UserDataModelMongo.class);
        return Optional.ofNullable(UserMapperMongo.toDomain(found));
    }

    @Override
    public List<User> searchUsers(Page page, SearchUsersQuery query) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (query.getUsername() != null && !query.getUsername().isBlank()) {
            criteriaList.add(Criteria.where("username").regex(query.getUsername(), "i"));
        }

        if (query.getFullName() != null && !query.getFullName().isBlank()) {
            criteriaList.add(Criteria.where("name").regex(query.getFullName(), "i"));
        }

        Criteria combinedCriteria = new Criteria();
        if (!criteriaList.isEmpty()) {
            combinedCriteria = new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
        }

        Query mongoQuery = new Query(combinedCriteria);
        mongoQuery.skip((long) (page.getNumber() - 1) * page.getLimit());
        mongoQuery.limit(page.getLimit());

        return mongoTemplate.find(mongoQuery, UserDataModelMongo.class).stream()
                .map(UserMapperMongo::toDomain)
                .toList();
    }

    @Override
    public List<User> findByNameName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.find(query, UserDataModelMongo.class).stream()
                .map(UserMapperMongo::toDomain)
                .toList();
    }

    @Override
    public List<User> findByNameNameContains(String name) {
        Query query = new Query(Criteria.where("name").regex(name, "i"));
        return mongoTemplate.find(query, UserDataModelMongo.class).stream()
                .map(UserMapperMongo::toDomain)
                .toList();
    }

    @Override
    public void delete(User user) {
        Query query = new Query(Criteria.where("username").is(user.getUsername()));
        mongoTemplate.remove(query, UserDataModelMongo.class);
    }

    @Override
    public Iterable<User> findAll() {
        return mongoTemplate.findAll(UserDataModelMongo.class).stream()
                .map(UserMapperMongo::toDomain)
                .toList();
    } 
}
