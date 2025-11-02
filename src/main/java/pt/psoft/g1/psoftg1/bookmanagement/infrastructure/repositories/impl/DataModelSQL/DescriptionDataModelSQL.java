package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelSQL;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;

@Entity
@Table(name = "descriptions")
@Getter
@NoArgsConstructor
public class DescriptionDataModelSQL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4096)
    private String description;

    public DescriptionDataModelSQL(Description description) {
        this.description = (description != null) ? description.toString() : null;
    }
}
