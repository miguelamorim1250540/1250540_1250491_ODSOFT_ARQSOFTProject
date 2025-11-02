package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelSQL.IsbnDataModelSQL;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;

public class IsbnMapper {

    public static IsbnDataModelSQL toDataModel(Isbn isbn) {
        if (isbn == null) return null;
        return new IsbnDataModelSQL(isbn.toString());
    }

    public static Isbn toDomain(IsbnDataModelSQL entity) {
        if (entity == null) return null;
        return new Isbn(entity.getIsbn());
    }
}
