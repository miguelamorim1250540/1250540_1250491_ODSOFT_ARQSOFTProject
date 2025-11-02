package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.RepositoryMongoDB;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.DataModel.UserDataModelMongo;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.DataModel.UserDataModelSQL;

public interface MongoDBUserRepository extends MongoRepository<UserDataModelMongo, String>{
    Optional<UserDataModelSQL> findByUsername(String username);
    List<UserDataModelSQL> findByNameName(String name);
    List<UserDataModelSQL> findByNameNameContains(String name);
}
