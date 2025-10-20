package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.mongodb;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

@Document(collection = "books")
public class BookDocument {
    @Id
    private String bookId;

    @Getter
    private Long version;

    @Getter
    private String isbn;

    @Getter
    private String title;

    @Getter
    private String description;

    @Getter
    private String genre;

    @Getter
    private List<String> authorIds;

    @Getter
    private String photoUrl;

    public BookDocument (String isbn, String title, String description, String genre, List<String> authorIds, String photoUrl) {
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.authorIds = authorIds;
        this.photoUrl = photoUrl;
    }
}
