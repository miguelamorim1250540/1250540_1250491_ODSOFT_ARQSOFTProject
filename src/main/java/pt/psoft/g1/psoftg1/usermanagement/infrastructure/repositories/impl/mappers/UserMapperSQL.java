package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.DataModel.UserDataModelSQL;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.shared.model.Name;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE // ignora quaisquer campos não mapeados
)
public interface UserMapperSQL {

    // De DataModel → Domínio
    @Mapping(source = "pk", target = "id")
    User toDomain(UserDataModelSQL entity);

    // De Domínio → DataModel
    @Mapping(source = "id", target = "pk")
    UserDataModelSQL toEntity(User domain);

    // Conversões em lista
    List<User> toDomainList(List<UserDataModelSQL> entities);
    List<UserDataModelSQL> toEntityList(List<User> users);

    // Conversões manuais para o Value Object Name
    default String map(Name name) {
        return name == null ? null : name.getName();
    }

    default Name map(String name) {
        return name == null ? null : new Name(name);
    }
}
