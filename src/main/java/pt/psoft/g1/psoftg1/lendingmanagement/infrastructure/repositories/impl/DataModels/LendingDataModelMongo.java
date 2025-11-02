package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;

@Document(collection = "lendings")
public class LendingDataModelMongo {
    @Id
    private String id;

    @Getter
    private String lendingNumber;
    @Getter
    private LocalDate startDate;
    @Getter
    private LocalDate limitDate;
    @Getter
    private LocalDate returnedDate;
    @Getter
    private int fineValuePerDayInCents;
    @Getter
    private String bookId;
    @Getter
    private String readerId;

    public LendingDataModelMongo(Lending lending){
        this.lendingNumber = lending.getLendingNumber().toString();
        this.startDate = lending.getStartDate();
        this.limitDate = lending.getLimitDate();
        this.returnedDate = lending.getReturnedDate();
        this.fineValuePerDayInCents = lending.getFineValuePerDayInCents();
        this.bookId = lending.getBook().getIsbn();
        this.readerId = lending.getReaderDetails().getReaderNumber();
    }
}
