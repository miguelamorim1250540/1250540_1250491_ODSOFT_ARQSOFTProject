package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mappers;

import java.util.stream.Collectors;

import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mappers.AuthorMapperRedis;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelRedis.BookDataModelRedis;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.mappers.GenreMapperRedis;

public class BookMapperRedis {
    public static BookDataModelRedis toDataModel(Book book) {
        if (book == null) return null;

        BookDataModelRedis dataModel = new BookDataModelRedis(book);
        
        return dataModel;
    }

    public static Book toDomain(BookDataModelRedis dataModel) {
        if (dataModel == null) return null;

        Book domainBook = new Book(
                dataModel.getIsbn(),
                dataModel.getTitle(),
                dataModel.getDescription(),
                GenreMapperRedis.toDomain(dataModel.getGenre()),
                dataModel.getAuthors().stream().map(authorMongo -> AuthorMapperRedis.toDomain(authorMongo)).collect(Collectors.toList()),
                dataModel.getPhotoURI()
        );

        //domainBook.setId(dataModel.getId());
        domainBook.setVersion(dataModel.getVersion());

        return domainBook;
    }
}
