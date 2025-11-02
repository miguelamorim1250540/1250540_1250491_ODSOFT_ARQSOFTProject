package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels.LendingDataModelMongo;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingNumber;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

public class LendingMapperMongo {
    public static Lending toDomain(LendingDataModelMongo dataModel) {
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

    public static LendingDataModelMongo toDataModel(Lending domain) {
        if (domain == null) return null;

        LendingDataModelMongo dm = new LendingDataModelMongo(domain);
        return dm;
    }
}
