package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels.FineDataModelSQL;

import java.util.Optional;

@Profile("sqlRedis")
public interface SpringDataFineRepository extends CrudRepository<FineDataModelSQL, Long> {

    @Query("SELECT f FROM FineDataModelSQL f " +
           "WHERE f.lendingId = (" +
           "SELECT l.pk FROM LendingDataModelSQL l WHERE l.lendingNumber = :lendingNumber)")
    Optional<FineDataModelSQL> findByLendingNumber(String lendingNumber);
}
