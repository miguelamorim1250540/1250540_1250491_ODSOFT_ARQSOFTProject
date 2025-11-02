package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mappers;

import java.util.stream.Collectors;

import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mappers.AuthorMapperMongoDb;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelMongo.BookDataModelMongo;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.mappers.GenreMapperMongo;

public class BookMapperMongo {
    public static BookDataModelMongo toDataModel(Book book) {
        if (book == null) return null;

        BookDataModelMongo dataModel = new BookDataModelMongo(book);
        
        return dataModel;
    }

    public static Book toDomain(BookDataModelMongo dataModel) {
        if (dataModel == null) return null;

        Book domainBook = new Book(
                dataModel.getIsbn(),
                dataModel.getTitle(),
                dataModel.getDescription(),
                GenreMapperMongo.toDomain(dataModel.getGenre()),
                dataModel.getAuthors().stream().map(authorMongo -> AuthorMapperMongoDb.toDomain(authorMongo)).collect(Collectors.toList()),
                dataModel.getPhotoURI()
        );

        //domainBook.setId(dataModel.getId());
        domainBook.setVersion(dataModel.getVersion());

        return domainBook;
    }
}
