package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels.LendingDataModelSQL;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Profile("sqlRedis")
public interface SpringDataLendingRepository
        extends CrudRepository<LendingDataModelSQL, Long>, LendingRepoCustom {

    @Query("SELECT l FROM LendingDataModelSQL l WHERE l.lendingNumber = :lendingNumber")
    Optional<LendingDataModelSQL> findByLendingNumber(@Param("lendingNumber") String lendingNumber);

    @Query("SELECT l FROM LendingDataModelSQL l WHERE l.readerId = :readerId AND l.bookId = :bookId")
    List<LendingDataModelSQL> listByReaderNumberAndIsbn(@Param("readerId") Long readerId, @Param("bookId") Long bookId);

    @Query("SELECT COUNT(l) FROM LendingDataModelSQL l WHERE YEAR(l.startDate) = YEAR(CURRENT_DATE)")
    int getCountFromCurrentYear();

    @Query("SELECT l FROM LendingDataModelSQL l WHERE l.readerId = :readerId AND l.returnedDate IS NULL")
    List<LendingDataModelSQL> listOutstandingByReader(@Param("readerId") Long readerId);

    @Query(value = "SELECT AVG(DATEDIFF(day, l.start_date, l.returned_date)) FROM Lending l", nativeQuery = true)
    Double getAverageDuration();
}
