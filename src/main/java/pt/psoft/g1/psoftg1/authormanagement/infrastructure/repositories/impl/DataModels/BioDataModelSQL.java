package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;

@Entity
@Table(name = "bios")
@Data
@NoArgsConstructor
public class BioDataModelSQL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4096, nullable = false)
    private String bio;

    // Construtor auxiliar para converter do modelo de domínio
    public BioDataModelSQL(Bio bioDomain) {
        this.bio = bioDomain.getBio();
    }

    // Método auxiliar para converter de volta para o modelo de domínio
    public Bio toDomain() {
        return new Bio(this.bio);
    }
}