package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.AuthorDataModelSQL;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

public class AuthorMapper {

    // Converte de domínio -> JPA (para guardar na BD)
    public static AuthorDataModelSQL toDataModel(Author author) {
        if (author == null) return null;
        return new AuthorDataModelSQL(author);
    }

    // Converte de JPA -> domínio (para devolver ao serviço)
    public static Author toDomain(Object saved) {
        if (saved == null) return null;
        return ((AuthorDataModelSQL) saved).toDomain();
    }
}
