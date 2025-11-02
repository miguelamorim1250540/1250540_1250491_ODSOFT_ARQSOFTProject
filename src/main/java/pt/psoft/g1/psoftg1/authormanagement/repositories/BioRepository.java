package pt.psoft.g1.psoftg1.authormanagement.repositories;

import pt.psoft.g1.psoftg1.authormanagement.model.Bio;

import java.util.Optional;

public interface BioRepository {
    Bio save(Bio bio);
    Optional<Bio> findById(Long id);
    Iterable<Bio> findAll();
    void delete(Bio bio);
}
