package pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl.DataModels.PhotoDataModelSQL;


public interface SpringDataPhotoRepository extends CrudRepository<PhotoDataModelSQL, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM PhotoDataModelSQL p WHERE p.photoFile = :photoFile")
    void deleteByPhotoFile(String photoFile);
}
