package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.AuthorDataModelRedis;

@Repository
public interface SpringDataAuthorRepositoryRedis extends CrudRepository<AuthorDataModelRedis, Long> {
}

