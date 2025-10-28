package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.BioDataModelSQL;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;

public class BioMapper {

    // Domínio → DataModel (para gravar na BD)
    public static BioDataModelSQL toDataModel(Bio bio) {
        if (bio == null) return null;
        return new BioDataModelSQL(bio);
    }

    // DataModel → Domínio (para devolver à camada de serviço)
    public static Bio toDomain(BioDataModelSQL entity) {
        if (entity == null) return null;
        return entity.toDomain();
    }
}