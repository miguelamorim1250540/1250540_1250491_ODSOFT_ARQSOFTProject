package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.DataModels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "genres")
@Getter
@NoArgsConstructor
public class GenreDataModelSQL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String genre;

    public GenreDataModelSQL(String genre) {
        this.genre = genre;
    }
}
