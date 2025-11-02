package pt.psoft.g1.psoftg1.usermanagement.model.sql;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.shared.services.Page;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.SpringDataUserRepository;
import pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.mappers.UserMapperSQL;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;
import pt.psoft.g1.psoftg1.usermanagement.services.SearchUsersQuery;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class UserRepositorySQL implements UserRepository {

    private final SpringDataUserRepository jpaRepo;
    private final UserMapperSQL mapper;

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepo.findByUsername(username).map(mapper::toDomain);
    }

    @Override
    public List<User> findByNameName(String name) {
        return jpaRepo.findByNameName(name).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public <S extends User> S save(S user) {
        jpaRepo.save(mapper.toEntity(user));
        return user;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        var entityList = StreamSupport.stream(entities.spliterator(), false)
            .map(mapper::toEntity)
            .toList();
        jpaRepo.saveAll(entityList);
        return (List<S>) entities;
    }

    @Override
    public Iterable<User> findAll() {
        return jpaRepo.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<User> searchUsers(Page page, SearchUsersQuery query) {
        throw new UnsupportedOperationException("searchUsers not yet implemented for SQL");
    }

    @Override
    public List<User> findByNameNameContains(String name) {
        return jpaRepo.findByNameNameContains(name).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(User user) {
        jpaRepo.deleteById(user.getId());
    }
}
