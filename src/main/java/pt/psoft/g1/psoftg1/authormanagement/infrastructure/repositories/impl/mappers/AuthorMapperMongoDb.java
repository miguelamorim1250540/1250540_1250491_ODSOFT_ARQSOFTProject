package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.AuthorDataModelMongoDB;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

public class AuthorMapperMongoDb {
    public static AuthorDataModelMongoDB toDataModel(Author author) {
        if (author == null) return null;

        AuthorDataModelMongoDB dataModel = new AuthorDataModelMongoDB(author);
      
        return dataModel;
    }
    
    public static Author toDomain(AuthorDataModelMongoDB dataModel) {
        if (dataModel == null) return null;

        Author author = new Author(
                dataModel.getName(),
                dataModel.getBio(),
                dataModel.getPhotoURI()
        );

        author.setAuthorNumber(dataModel.getAuthorNumber());
        author.setVersion(dataModel.getVersion());

        return author;
    }
}
