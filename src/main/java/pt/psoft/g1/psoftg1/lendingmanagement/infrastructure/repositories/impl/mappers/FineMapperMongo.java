package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels.FineDataModelMongo;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;

public class FineMapperMongo {
    public static FineDataModelMongo toDataModel(Fine fine) {
        if (fine == null) return null;
        return new FineDataModelMongo(fine);
    }

    public static Fine toDomain(FineDataModelMongo dataModel) {
        if (dataModel == null) return null;
        Lending lending = new Lending(null, null, 0, 0, 0);
        Fine fine = new Fine(lending);
        return fine;
    }
}
