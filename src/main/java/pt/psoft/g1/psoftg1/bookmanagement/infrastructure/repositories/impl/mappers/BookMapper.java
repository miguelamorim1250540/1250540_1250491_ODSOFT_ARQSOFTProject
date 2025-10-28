package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelSQL.BookDataModelSQL;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;

public class BookMapper {

    // Domínio → DataModelSQL
    public static BookDataModelSQL toDataModel(Book book) {
        if (book == null) return null;
        return new BookDataModelSQL(book);
    }

    // DataModelSQL → Domínio
    public static Book toDomain(BookDataModelSQL dataModel) {
        if (dataModel == null) return null;
        return dataModel.toDomain();
    }
}
