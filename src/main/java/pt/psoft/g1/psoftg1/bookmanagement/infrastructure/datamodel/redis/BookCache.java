package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.redis;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

@RedisHash("Book")
public class BookCache implements Serializable{
    @Id
    @Getter
    private String id;

    @Getter
    private IsbnCache isbn;

    @Getter
    private TitleCache title;

    @Getter
    private DescriptionCache description;

    @Getter
    private Genre genre;

    @Getter
    private List<Author> authors;
    
    @Getter
    private String photoUrl;

    public BookCache (String id, IsbnCache isbn, TitleCache title, DescriptionCache description, Genre genre, List<Author> authors, String photoUrl) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.authors = authors;
        this.photoUrl = photoUrl;
    }
}
