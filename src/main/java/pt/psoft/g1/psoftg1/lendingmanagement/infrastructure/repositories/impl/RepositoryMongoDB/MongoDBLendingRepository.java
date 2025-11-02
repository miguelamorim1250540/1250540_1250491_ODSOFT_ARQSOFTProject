package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.RepositoryMongoDB;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels.LendingDataModelMongo;

public interface MongoDBLendingRepository extends MongoRepository<LendingDataModelMongo, String>{
    @Query("{ 'lendingNumber': ?0 }")
    Optional<LendingDataModelMongo> findByLendingNumber(@Param("lendingNumber") String lendingNumber);

    @Query("{ 'readerId': ?0, 'bookId': ?1 }")
    List<LendingDataModelMongo> listByReaderNumberAndIsbn(@Param("readerId") Long readerId, @Param("bookId") Long bookId);

    @Query("{ 'limitDate': { '$gte': { '$dateFromString': { 'dateString': ?0 } } } }")
    int getCountFromCurrentYear();

    @Query("{ 'readerId': ?0, 'returnedDate': null }")
    List<LendingDataModelMongo> listOutstandingByReader(@Param("readerId") Long readerId);

    Double getAverageDuration();
}
