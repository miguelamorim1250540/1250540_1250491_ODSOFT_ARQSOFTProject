package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.RepositorySQL.AuthorDataModelRedis;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

public class AuthorMapperRedis {

    // Domínio -> Redis
    public static AuthorDataModelRedis toDataModel(Author author) {
        if (author == null) return null;
        return new AuthorDataModelRedis(author);
    }

    // Redis -> Domínio
    public static Author toDomain(AuthorDataModelRedis dataModel) {
        if (dataModel == null) return null;
        return dataModel.toDomain();
    }
}
