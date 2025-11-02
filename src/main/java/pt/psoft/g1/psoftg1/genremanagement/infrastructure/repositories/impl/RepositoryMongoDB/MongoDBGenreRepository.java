package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.RepositoryMongoDB;

import org.springframework.data.mongodb.repository.MongoRepository;

import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.DataModels.GenreDataModelMongo;

public interface MongoDBGenreRepository extends MongoRepository<GenreDataModelMongo, String> {
    
}
