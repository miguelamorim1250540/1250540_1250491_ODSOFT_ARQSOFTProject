package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

@Document(collection = "authors")
public class AuthorDataModelMongoDB {
    @Id
    @Getter
    private String authorNumber;
    
    @Getter
    private String name;
    
    @Getter
    private String bio;
    
    @Getter
    private String photoURI;
    
    @Getter
    private long version;

    public AuthorDataModelMongoDB(Author author) {
        this.authorNumber = author.getAuthorNumber();
        this.bio = author.getBio();
        this.name = author.getName();
        this.photoURI = author.getPhotoURI();
        this.version = author.getVersion();
    }
}
