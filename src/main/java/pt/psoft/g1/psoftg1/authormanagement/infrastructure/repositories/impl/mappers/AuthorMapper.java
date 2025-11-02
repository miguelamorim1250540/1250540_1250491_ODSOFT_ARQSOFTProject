package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.AuthorDataModelSQL;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;

public class AuthorMapper {

    // 🔁 Domínio → DataModel (para gravar na BD)
    public static AuthorDataModelSQL toDataModel(Author author) {
        if (author == null) return null;

        AuthorDataModelSQL dataModel = new AuthorDataModelSQL();
        dataModel.setAuthorNumber(author.getAuthorNumber());
        dataModel.setName(author.getName());
        dataModel.setBio(author.getBio() != null ? author.getBio() : null);
        dataModel.setPhotoURI(author.getPhotoInternal());
        dataModel.setVersion(author.getVersion());

        return dataModel;
    }

    // 🔁 DataModel → Domínio (para devolver à camada de serviço)
    public static Author toDomain(AuthorDataModelSQL dataModel) {
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
