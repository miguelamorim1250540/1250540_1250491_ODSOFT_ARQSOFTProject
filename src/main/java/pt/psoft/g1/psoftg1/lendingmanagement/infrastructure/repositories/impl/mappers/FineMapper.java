package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl.DataModels.FineDataModelSQL;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;

public class FineMapper {

    public static FineDataModelSQL toDataModel(Fine fine) {
        if (fine == null) return null;
        return new FineDataModelSQL(fine);
    }

    public static Fine toDomain(FineDataModelSQL dataModel) {
        if (dataModel == null) return null;
        Lending lending = new Lending(null, null, 0, 0, 0); // aqui criarias um stub mínimo se necessário
        Fine fine = new Fine(lending);
        return fine;
    }
}
