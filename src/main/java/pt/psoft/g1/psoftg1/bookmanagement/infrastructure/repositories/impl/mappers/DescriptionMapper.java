package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mappers;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelSQL.DescriptionDataModelSQL;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;

public class DescriptionMapper {

    // Domínio → DataModel (para gravar na BD)
    public static DescriptionDataModelSQL toDataModel(Description description) {
        if (description == null) return null;
        return new DescriptionDataModelSQL(description);
    }

    // DataModel → Domínio (para devolver à camada de serviço)
    public static Description toDomain(DescriptionDataModelSQL entity) {
        if (entity == null) return null;
        return new Description(entity.getDescription());
    }
}
