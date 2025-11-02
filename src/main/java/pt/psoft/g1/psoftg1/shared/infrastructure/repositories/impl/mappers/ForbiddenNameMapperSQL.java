package pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl.DataModels.ForbiddenNameDataModelSQL;
import pt.psoft.g1.psoftg1.shared.model.ForbiddenName;

@Mapper(componentModel = "spring")
public interface ForbiddenNameMapperSQL {
    @Mapping(target = "forbiddenName", source = "forbiddenName")
    ForbiddenNameDataModelSQL toDataModel(ForbiddenName domain);

    // Aqui o MapStruct chama new ForbiddenName(model.getForbiddenName())
    default ForbiddenName toDomain(ForbiddenNameDataModelSQL model) {
        return new ForbiddenName(model.getForbiddenName());
    }
}
