package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.mongodb;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

@Document(collection = "books")
public class BookDocument {
    @Id
    @Getter
    private String bookId;

    @Getter
    private Long version;

    @Getter
    private IsbnDocument isbn;

    @Getter
    private TitleDocument title;

    @Getter
    private DescriptionDocument description;

    @Getter
    private Genre genre;

    @Getter
    private List<Author> authors;

    @Getter
    private String photoUrl;

    public BookDocument (String bookId, IsbnDocument isbn, TitleDocument title, DescriptionDocument description, Genre genre, List<Author> authors, String photoUrl) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.authors = authors;
        this.photoUrl = photoUrl;
    }
}
