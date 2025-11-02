package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.RepositoryMongoDB;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.AuthorDataModelMongoDB;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.AuthorDataModelSQL;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

@Profile("mongoRedis")
@Repository
public interface MongoDBAuthorRepository extends MongoRepository<AuthorDataModelMongoDB, String>{
    Optional<Author> findByAuthorNumber(Long authorNumber);

    List<AuthorDataModelSQL> findByNameStartingWithIgnoreCase(String name);

    List<AuthorDataModelSQL> findByNameIgnoreCase(String name);

    // Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageable);

    Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageable);

    List<Author> findCoAuthorsByAuthorNumber(Long authorNumber);
}
