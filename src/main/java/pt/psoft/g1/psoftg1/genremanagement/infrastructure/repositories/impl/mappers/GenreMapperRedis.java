package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.DataModels.GenreDataModelRedis;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

public class GenreMapperRedis {

    public static GenreDataModelRedis toDataModel(Genre genre) {
        if (genre == null) return null;
        return new GenreDataModelRedis(genre);
    }

    public static Genre toDomain(GenreDataModelRedis dataModel) {
        if (dataModel == null) return null;
        return new Genre(dataModel.getGenre(), null);
    }
}
