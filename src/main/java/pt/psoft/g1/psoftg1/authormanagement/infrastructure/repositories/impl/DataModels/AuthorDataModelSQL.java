package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
public class AuthorDataModelSQL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String authorNumber;

    @Column(nullable = false)
    private String name;

    @Column(length = 4096)
    private String bio;

    private String photoURI;

    private long version;
}
