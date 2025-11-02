package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;

@Entity
@Table(name = "fines")
@Getter
@NoArgsConstructor
public class FineDataModelSQL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(nullable = false, updatable = false)
    private int fineValuePerDayInCents;

    @Column(nullable = false)
    private int centsValue;

    @Column(nullable = false, unique = true)
    private Long lendingId;

    private Object lendingNumber;

    public FineDataModelSQL(Fine fine) {
        this.fineValuePerDayInCents = fine.getFineValuePerDayInCents();
        this.centsValue = fine.getCentsValue();
        this.lendingNumber = fine.getLending() != null
                ? fine.getLending().getLendingNumber().toString()
                : null;
    }
}
