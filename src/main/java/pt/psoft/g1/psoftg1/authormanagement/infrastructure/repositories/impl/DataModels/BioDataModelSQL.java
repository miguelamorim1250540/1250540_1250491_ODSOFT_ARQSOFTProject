package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
