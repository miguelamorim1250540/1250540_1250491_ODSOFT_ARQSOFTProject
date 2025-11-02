package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelMongo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.AuthorDataModelMongoDB;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mappers.AuthorMapperMongoDb;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.DataModels.GenreDataModelMongo;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.mappers.GenreMapperMongo;

@Document(collection = "books")
public class BookDataModelMongo {
    @Id
    @Getter
    private String isbn;
    @Getter
    private String title;
    @Getter
    private String description;
    @Getter
    private GenreDataModelMongo genre;
    @Getter
    private List<AuthorDataModelMongoDB> authors;
    @Getter
    private String photoURI;
    @Getter
    private Long version;

    public BookDataModelMongo(Book book){
        this.isbn = book.getIsbn();
        this.title = book.getTitle().getTitle();
        this.description = book.getDescription();
        this.genre = GenreMapperMongo.toDataModel(book.getGenre());
        this.photoURI = book.getPhotoURI();
        this.version = book.getVersion();

        this.authors = book.getAuthors().stream().map(author -> AuthorMapperMongoDb.toDataModel(author)).collect(Collectors.toList());
    }
}
