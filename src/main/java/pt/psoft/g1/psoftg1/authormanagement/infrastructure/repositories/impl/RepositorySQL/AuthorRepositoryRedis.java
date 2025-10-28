package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.RepositorySQL;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.AuthorDataModelRedis;

@Repository
public interface AuthorRepositoryRedis extends CrudRepository<AuthorDataModelRedis, Long> {
}