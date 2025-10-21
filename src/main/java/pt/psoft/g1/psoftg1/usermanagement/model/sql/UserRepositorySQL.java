package pt.psoft.g1.psoftg1.usermanagement.model.sql;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.shared.services.Page;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.SpringDataUserRepository;
import pt.psoft.g1.psoftg1.usermanagement.services.SearchUsersQuery;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositorySQL implements UserRepository {

    private final SpringDataUserRepository jpaRepo;

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepo.findByUsername(username);
    }

    @Override
    public List<User> findByNameName(String name) {
        return jpaRepo.findByNameName(name);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepo.findById(id);
    }

    @Override
    public User save(User user) {
        return jpaRepo.save(user);
    }

    @Override
    public Iterable<User> findAll() {
        return jpaRepo.findAll();
    }

    @Override
    public List<User> searchUsers(Page page, SearchUsersQuery query) {
        throw new UnsupportedOperationException("searchUsers not yet implemented for SQL");
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public List<User> findByNameNameContains(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByNameNameContains'");
    }

    @Override
    public void delete(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}
