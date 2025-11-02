package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.DataModels.ReaderDetailsDataModelSQL;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderBookCountDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpringDataReaderRepository extends CrudRepository<ReaderDetailsDataModelSQL, Long>, ReaderDetailsRepoCustom  {

    @Query("SELECT r FROM ReaderDetailsDataModelSQL r WHERE r.readerNumber.readerNumber = :readerNumber")
    Optional<ReaderDetailsDataModelSQL> findByReaderNumber(@Param("readerNumber") String readerNumber);

    @Query("SELECT r FROM ReaderDetailsDataModelSQL r WHERE r.phoneNumber.phoneNumber = :phoneNumber")
    List<ReaderDetailsDataModelSQL> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT r FROM ReaderDetailsDataModelSQL r JOIN User u ON r.reader.id = u.id WHERE u.username = :username")
    Optional<ReaderDetailsDataModelSQL> findByUsername(@Param("username") String username);

    @Query("SELECT r FROM ReaderDetailsDataModelSQL r JOIN User u ON r.reader.id = u.id WHERE u.id = :userId")
    Optional<ReaderDetailsDataModelSQL> findByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(r) FROM ReaderDetailsDataModelSQL r JOIN User u ON r.reader.id = u.id WHERE YEAR(u.createdAt) = YEAR(CURRENT_DATE)")
    int getCountFromCurrentYear();

    @Query("SELECT r FROM ReaderDetailsDataModelSQL r JOIN Lending l ON l.readerDetails.pk = r.pk GROUP BY r ORDER BY COUNT(l) DESC")
    Page<ReaderDetailsDataModelSQL> findTopReaders(Pageable pageable);

    @Query("SELECT NEW pt.psoft.g1.psoftg1.readermanagement.services.ReaderBookCountDTO(r, COUNT(l)) " +
           "FROM ReaderDetailsDataModelSQL r JOIN Lending l ON l.readerDetails.pk = r.pk " +
           "JOIN Book b ON b.pk = l.book.pk " +
           "JOIN Genre g ON g.pk = b.genre.pk " +
           "WHERE g.genre = :genre " +
           "AND l.startDate >= :startDate " +
           "AND l.startDate <= :endDate " +
           "GROUP BY r.pk ORDER BY COUNT(l.pk) DESC")
    Page<ReaderBookCountDTO> findTopByGenre(Pageable pageable,
                                            @Param("genre") String genre,
                                            @Param("startDate") LocalDate startDate,
                                            @Param("endDate") LocalDate endDate);
}
