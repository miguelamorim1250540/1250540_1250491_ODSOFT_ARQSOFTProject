package pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl.DataModels.ForbiddenNameDataModelSQL;

import java.util.List;
import java.util.Optional;

public interface SpringDataForbiddenNameRepository extends CrudRepository<ForbiddenNameDataModelSQL, Long> {

    @Query("SELECT fn FROM ForbiddenNameDataModelSQL fn WHERE :pat LIKE CONCAT('%', fn.forbiddenName, '%')")
    List<ForbiddenNameDataModelSQL> findByForbiddenNameIsContained(String pat);

    @Query("SELECT fn FROM ForbiddenNameDataModelSQL fn WHERE fn.forbiddenName = :forbiddenName")
    Optional<ForbiddenNameDataModelSQL> findByForbiddenName(String forbiddenName);

    @Modifying
    @Transactional
    @Query("DELETE FROM ForbiddenNameDataModelSQL fn WHERE fn.forbiddenName = :forbiddenName")
    int deleteForbiddenName(String forbiddenName);
}
