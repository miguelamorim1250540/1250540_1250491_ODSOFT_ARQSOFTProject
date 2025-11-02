package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelSQL;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
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

    @ManyToMany
    @JoinTable(
        name = "book_authors",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    private String photoURI;

    private Long version;
}
