package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.BioDataModelSQL;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;

public class BioMapper {

    // 🔁 Domínio → DataModel
    public static BioDataModelSQL toDataModel(Bio bio) {
        if (bio == null) return null;

        BioDataModelSQL dataModel = new BioDataModelSQL();
        dataModel.setBio(bio.getBio());
        return dataModel;
    }

    // 🔁 DataModel → Domínio
    public static Bio toDomain(BioDataModelSQL dataModel) {
        if (dataModel == null) return null;

        return new Bio(dataModel.getBio());
    }
}
