package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.mappers;

import org.springframework.stereotype.Component;

import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels.LendingDataModelSQL;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingNumber;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

@Component
public class LendingMapper {

    public Lending toDomain(LendingDataModelSQL dataModel) {
        if (dataModel == null) return null;

    Book book = null;
    ReaderDetails reader = null;
    return new Lending(
            book,
            reader,
            new LendingNumber(dataModel.getLendingNumber()),
            dataModel.getStartDate(),
            dataModel.getLimitDate(),
            dataModel.getFineValuePerDayInCents()
    );
    }

    public LendingDataModelSQL toDataModel(Lending domain) {
        if (domain == null) return null;

        LendingDataModelSQL dm = new LendingDataModelSQL();
        dm.setLendingNumber(domain.getLendingNumber().toString());
        dm.setStartDate(domain.getStartDate());
        dm.setLimitDate(domain.getLimitDate());
        dm.setReturnedDate(domain.getReturnedDate());
        dm.setFineValuePerDayInCents(domain.getFineValuePerDayInCents());
        // aqui convertes Book e ReaderDetails para IDs
        return dm;
    }
}
