package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.DataModel.UserDataModelSQL;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-02T00:05:35+0000",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251023-0518, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class UserMapperSQLImpl implements UserMapperSQL {

    @Override
    public User toDomain(UserDataModelSQL entity) {
        if ( entity == null ) {
            return null;
        }

        String username = null;
        String password = null;

        username = entity.getUsername();
        password = entity.getPassword();

        User user = new User( username, password );

        user.setId( entity.getPk() );
        user.setEnabled( entity.isEnabled() );
        user.setName( map( entity.getName() ) );
        if ( user.getAuthorities() != null ) {
            Set<Role> set = entity.getAuthorities();
            if ( set != null ) {
                user.getAuthorities().addAll( set );
            }
        }

        return user;
    }

    @Override
    public UserDataModelSQL toEntity(User domain) {
        if ( domain == null ) {
            return null;
        }

        UserDataModelSQL userDataModelSQL = new UserDataModelSQL();

        userDataModelSQL.setPk( domain.getId() );
        userDataModelSQL.setEnabled( domain.isEnabled() );
        userDataModelSQL.setName( domain.getName() );
        userDataModelSQL.setPassword( domain.getPassword() );
        userDataModelSQL.setUsername( domain.getUsername() );
        if ( userDataModelSQL.getAuthorities() != null ) {
            Set<Role> set = domain.getAuthorities();
            if ( set != null ) {
                userDataModelSQL.getAuthorities().addAll( set );
            }
        }

        return userDataModelSQL;
    }

    @Override
    public List<User> toDomainList(List<UserDataModelSQL> entities) {
        if ( entities == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( entities.size() );
        for ( UserDataModelSQL userDataModelSQL : entities ) {
            list.add( toDomain( userDataModelSQL ) );
        }

        return list;
    }

    @Override
    public List<UserDataModelSQL> toEntityList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDataModelSQL> list = new ArrayList<UserDataModelSQL>( users.size() );
        for ( User user : users ) {
            list.add( toEntity( user ) );
        }

        return list;
    }
}
