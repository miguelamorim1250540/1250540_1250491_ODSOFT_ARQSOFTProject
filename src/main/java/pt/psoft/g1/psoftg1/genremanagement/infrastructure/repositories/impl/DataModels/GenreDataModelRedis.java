package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.DataModels;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Genre") // Define o prefixo usado nas keys do Redis
public class GenreDataModelRedis {

    @Id
    private String id; // geralmente o nome do género

    private String genre;

    public GenreDataModelRedis(Genre genre) {
        this.id = genre.toString(); // a key será o nome do género
        this.genre = genre.toString();
    }
}
