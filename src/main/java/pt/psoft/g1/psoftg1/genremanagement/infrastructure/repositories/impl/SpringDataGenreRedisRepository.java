package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.DataModels.GenreDataModelRedis;

@Profile("sqlRedis")
public interface SpringDataGenreRedisRepository extends CrudRepository<GenreDataModelRedis, String> {
}
