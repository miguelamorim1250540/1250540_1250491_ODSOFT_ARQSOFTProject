package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;

@Document(collection = "fines")
public class FineDataModelMongo {
    @Id
    private String id;

    @Getter
    private int fineValuePerDayInCents;

    @Getter
    private int centsValue;

    @Getter
    private String lendingId;

    @Getter
    private Object lendingNumber;

    public FineDataModelMongo(Fine fine) {
        this.fineValuePerDayInCents = fine.getFineValuePerDayInCents();
        this.centsValue = fine.getCentsValue();
        this.lendingNumber = fine.getLending() != null
                ? fine.getLending().getLendingNumber().toString()
                : null;
    }
}
