package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lending")
@Getter
@Setter
public class LendingDataModelSQL {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    private String lendingNumber;
    private LocalDate startDate;
    private LocalDate limitDate;
    private LocalDate returnedDate;
    private int fineValuePerDayInCents;

    private Long bookId;
    private Long readerId;
}
