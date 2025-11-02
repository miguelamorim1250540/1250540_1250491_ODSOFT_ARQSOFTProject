package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.RepositoryMongoDB;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels.FineDataModelMongo;

public interface MongoDBFineRepository extends MongoRepository<FineDataModelMongo, String>{
    @Query("{'lendingNumber': ?0 }")
    Optional<FineDataModelMongo> findByLendingNumber(String lendingNumber);
}
