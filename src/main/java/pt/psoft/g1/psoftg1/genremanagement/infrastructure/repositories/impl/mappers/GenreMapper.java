package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.DataModels.GenreDataModelSQL;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

public class GenreMapper {

    // Domínio → DataModel (guardar na BD)
    public static GenreDataModelSQL toDataModel(Genre genre) {
        if (genre == null) return null;
        return new GenreDataModelSQL(genre.toString());
    }

    // DataModel → Domínio (devolver à camada de serviço)
    public static Genre toDomain(GenreDataModelSQL entity) {
        if (entity == null) return null;
        return new Genre(entity.getGenre(), null);
    }
}
