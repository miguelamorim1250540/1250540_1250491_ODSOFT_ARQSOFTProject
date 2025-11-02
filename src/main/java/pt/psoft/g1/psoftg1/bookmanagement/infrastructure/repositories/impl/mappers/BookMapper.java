package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelSQL.BookDataModelSQL;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;

public class BookMapper {

    // 🔁 Domínio → DataModelSQL
    public static BookDataModelSQL toDataModel(Book book) {
        if (book == null) return null;

        BookDataModelSQL dataModel = new BookDataModelSQL();
        dataModel.setId(book.getId());
        dataModel.setIsbn(book.getIsbn());
        dataModel.setTitle(book.getTitle() != null ? book.getTitle().toString() : null);
        dataModel.setDescription(book.getDescription());
        dataModel.setGenre(book.getGenre());
        dataModel.setAuthors(book.getAuthors());
        dataModel.setPhotoURI(book.getPhotoURI());
        dataModel.setVersion(book.getVersion());
        return dataModel;
    }

    // 🔁 DataModelSQL → Domínio
    public static Book toDomain(BookDataModelSQL dataModel) {
        if (dataModel == null) return null;

        Book domainBook = new Book(
                dataModel.getIsbn(),
                dataModel.getTitle(),
                dataModel.getDescription(),
                dataModel.getGenre(),
                dataModel.getAuthors(),
                dataModel.getPhotoURI()
        );

        domainBook.setId(dataModel.getId());
        domainBook.setVersion(dataModel.getVersion());

        return domainBook;
    }
}
