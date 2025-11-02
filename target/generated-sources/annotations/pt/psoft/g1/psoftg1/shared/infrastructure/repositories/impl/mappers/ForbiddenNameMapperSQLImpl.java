package pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl.DataModels.ForbiddenNameDataModelSQL;
import pt.psoft.g1.psoftg1.shared.model.ForbiddenName;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-01T18:58:01+0000",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class ForbiddenNameMapperSQLImpl implements ForbiddenNameMapperSQL {

    @Override
    public ForbiddenNameDataModelSQL toDataModel(ForbiddenName domain) {
        if ( domain == null ) {
            return null;
        }

        ForbiddenNameDataModelSQL forbiddenNameDataModelSQL = new ForbiddenNameDataModelSQL();

        forbiddenNameDataModelSQL.setForbiddenName( domain.getForbiddenName() );

        return forbiddenNameDataModelSQL;
    }
}
