package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelSQL.IsbnDataModelSQL;

import java.util.Optional;

@Profile("sqlRedis")
public interface SpringDataIsbnRepository extends JpaRepository<IsbnDataModelSQL, String> {

    Optional<IsbnDataModelSQL> findByIsbn(String isbn);
}
