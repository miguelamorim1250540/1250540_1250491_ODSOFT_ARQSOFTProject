package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.DataModel;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

@Document(collection = "users")
public class UserDataModelMongo {
    @Id
    private String id;

    @Getter
    private String username;

    @Getter
    private String password;

    @Getter
    private String name;

    @Getter
    private final Set<Role> authorities = new HashSet<>();

    public UserDataModelMongo(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName().toString();
        //this.authorities = user.getAuthorities();
    }
}

