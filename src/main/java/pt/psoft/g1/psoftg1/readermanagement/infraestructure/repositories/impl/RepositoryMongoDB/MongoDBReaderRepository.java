package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.RepositoryMongoDB;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.DataModels.ReaderDetailsDataModelMongo;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.DataModels.ReaderDetailsDataModelSQL;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderBookCountDTO;

public interface MongoDBReaderRepository extends MongoRepository<ReaderDetailsDataModelMongo, String>{
    @Query("{ 'readerNumber': ?0 }")
    Optional<ReaderDetailsDataModelSQL> findByReaderNumber(@Param("readerNumber") String readerNumber);

    @Query("{ 'phoneNumber': ?0 }")
    List<ReaderDetailsDataModelSQL> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("{ 'reader.username': ?0 }")
    Optional<ReaderDetailsDataModelSQL> findByUsername(@Param("username") String username);

    @Query("{ 'reader.id': ?0 }")
    Optional<ReaderDetailsDataModelSQL> findByUserId(@Param("userId") Long userId);

    int getCountFromCurrentYear();

    Page<ReaderDetailsDataModelSQL> findTopReaders(Pageable pageable);

    @Query("{ 'interestList.genre': ?0, 'startDate': { '$gte': ?1, '$lte': ?2 } }")
    Page<ReaderBookCountDTO> findTopByGenre(Pageable pageable,
                                            @Param("genre") String genre,
                                            @Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);
}
