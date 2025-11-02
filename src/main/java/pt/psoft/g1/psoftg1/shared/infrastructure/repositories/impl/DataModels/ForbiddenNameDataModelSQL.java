package pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl.DataModels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.model.ForbiddenName;

@Entity
@Getter
@Setter
@Table(name = "forbidden_names")
public class ForbiddenNameDataModelSQL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String forbiddenName;

    public static ForbiddenNameDataModelSQL fromDomain(ForbiddenName domain) {
        ForbiddenNameDataModelSQL model = new ForbiddenNameDataModelSQL();
        model.setForbiddenName(domain.getForbiddenName());
        return model;
    }

    public ForbiddenName toDomain() {
        return new ForbiddenName(forbiddenName);
    }
}
