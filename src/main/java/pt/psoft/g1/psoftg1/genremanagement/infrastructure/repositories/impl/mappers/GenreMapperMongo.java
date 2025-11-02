package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.DataModels.GenreDataModelMongo;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

public class GenreMapperMongo {
    public static GenreDataModelMongo toDataModel(Genre genre) {
        if (genre == null) return null;
        return new GenreDataModelMongo(genre);
    }

    public static Genre toDomain(GenreDataModelMongo entity) {
        if (entity == null) return null;
        return new Genre(entity.getGenre(), null);
    }
}
