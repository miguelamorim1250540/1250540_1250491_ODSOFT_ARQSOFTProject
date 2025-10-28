package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelSQL;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class BookDataModelSQL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(length = 4096)
    private String description;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    // ⚠️ Se a relação com Author for ManyToMany
    // (com tabela intermédia)
    @ManyToMany
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    private String photoURI;

    private Long version;

    // 🔁 Construtor para converter do domínio → JPA
    public BookDataModelSQL(Book book) {
        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.title = book.getTitle().toString();
        this.description = book.getDescription();
        this.genre = book.getGenre();
        this.authors = book.getAuthors();
        this.photoURI = book.getPhotoURI();
        this.version = book.getVersion();
    }

    // 🔁 Método para converter do JPA → domínio
    public Book toDomain() {
        return new Book(
                this.isbn,
                this.title,
                this.description,
                this.genre,
                this.authors,
                this.photoURI
        );
    }
}