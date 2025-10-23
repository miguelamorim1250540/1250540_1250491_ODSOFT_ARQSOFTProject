package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.RepositorySQL;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

@RedisHash("Author")
@Data
@NoArgsConstructor
public class AuthorDataModelRedis {

    @Id
    private Long authorNumber;
    private String name;
    private String bio;
    private String photoURI;
    private long version;

    public AuthorDataModelRedis(Author author) {
        this.authorNumber = author.getId();
        this.name = author.getName();
        // this.bio = author.getBio().toString();
        this.photoURI = author.getPhotoInternal();
        this.version = author.getVersion();
    }

    public Author toDomain() {
        Author author = new Author(this.name, this.photoURI); //this.bio
        author.setVersion(this.version);
        return author;
    }
}
