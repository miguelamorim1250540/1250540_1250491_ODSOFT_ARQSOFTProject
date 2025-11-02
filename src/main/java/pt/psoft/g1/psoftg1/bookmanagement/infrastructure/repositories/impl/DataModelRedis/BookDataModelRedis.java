package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelRedis;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.AuthorDataModelRedis;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mappers.AuthorMapperRedis;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.DataModels.GenreDataModelRedis;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.mappers.GenreMapperRedis;

@RedisHash("Book")
public class BookDataModelRedis implements Serializable {
    @Id
    @Getter
    private String isbn;
    @Getter
    private String title;
    @Getter
    private String description;
    @Getter
    private GenreDataModelRedis genre;
    @Getter
    private List<AuthorDataModelRedis> authors;
    @Getter
    private String photoURI;
    @Getter
    private Long version;

    public BookDataModelRedis(Book book){
        this.isbn = book.getIsbn();
        this.title = book.getTitle().getTitle();
        this.description = book.getDescription();
        this.genre = GenreMapperRedis.toDataModel(book.getGenre());
        this.photoURI = book.getPhotoURI();
        this.version = book.getVersion();

        this.authors = book.getAuthors().stream().map(author -> AuthorMapperRedis.toDataModel(author)).collect(Collectors.toList());
    }
}
