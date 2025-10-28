package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

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

    @Column(length = 4096)
    private String bio;

    private String photoURI;

    private long version;

    // Construtor auxiliar para converter do modelo de domínio
    public AuthorDataModelSQL(Author author) {
        this.authorNumber = author.getAuthorNumber();
        this.name = author.getName();
        this.bio = author.getBio(); // agora é String
        this.photoURI = author.getPhotoInternal();
        this.version = author.getVersion();
    }

    // Método auxiliar para converter de volta para o modelo de domínio
    public Author toDomain() {
        Author author = new Author(this.name, this.bio, this.photoURI);
        author.setVersion(this.version);
        return author;
    }
}
