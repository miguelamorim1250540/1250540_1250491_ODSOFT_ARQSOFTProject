package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.RepositorySQL.AuthorDataModelRedis;

@Repository
public interface AuthorRepositoryRedis extends CrudRepository<AuthorDataModelRedis, Long> {
}