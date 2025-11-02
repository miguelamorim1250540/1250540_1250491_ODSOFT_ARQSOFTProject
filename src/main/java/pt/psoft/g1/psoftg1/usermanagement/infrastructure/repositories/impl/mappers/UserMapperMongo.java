package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.mappers;



import pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.DataModel.UserDataModelMongo;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

public class UserMapperMongo {
    public static User toDomain(UserDataModelMongo userMongo){
        User user = new User(userMongo.getUsername(), userMongo.getPassword());
        user.setName(userMongo.getName());

        return user;
    }

    public static UserDataModelMongo toDataModel(User user){
        return new UserDataModelMongo(user);
    }
}
