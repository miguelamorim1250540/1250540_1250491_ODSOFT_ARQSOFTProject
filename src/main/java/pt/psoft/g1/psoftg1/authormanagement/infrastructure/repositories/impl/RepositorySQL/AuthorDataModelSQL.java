package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.RepositorySQL;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
public class AuthorDataModelSQL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorNumber;

    @Column(nullable = false)
    private String name;

//     @Embedded
// //    private Bio bio;
//     // @Column(length = 4096)
//     private String bio;

    private String photoURI;

    private long version;

    // Construtor auxiliar para converter do modelo de domínio
    public AuthorDataModelSQL(Author author) {
        this.authorNumber = author.getId();
        this.name = author.getName();
        // this.bio = author.getBio();
        this.photoURI = author.getPhotoInternal();
        this.version = author.getVersion();
    }

    // Método auxiliar para converter de volta para o modelo de domínio this.bio,
    public Author toDomain() {
        Author author = new Author(this.name, this.photoURI);
        return author;
    }
}