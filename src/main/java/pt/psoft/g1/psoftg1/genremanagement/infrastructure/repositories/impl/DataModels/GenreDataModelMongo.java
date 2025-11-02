package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.DataModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

@Document(collection = "genres")
public class GenreDataModelMongo {
    @Id
    @Getter
    private String id; // geralmente o nome do género

    @Getter
    private String genre;

    public GenreDataModelMongo(Genre genre) {
        this.id = genre.toString(); // a key será o nome do género
        this.genre = genre.toString();
    }
}
