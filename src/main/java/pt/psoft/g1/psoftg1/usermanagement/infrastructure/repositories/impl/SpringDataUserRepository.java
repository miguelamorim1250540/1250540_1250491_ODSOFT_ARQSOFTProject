package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

import java.util.List;
import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "users")
public interface SpringDataUserRepository extends CrudRepository<User, Long> {

    @Override
    @CacheEvict(allEntries = true)
    <S extends User> List<S> saveAll(Iterable<S> entities);

    @Override
    @Caching(evict = {
            @CacheEvict(key = "#p0.id", condition = "#p0.id != null"),
            @CacheEvict(key = "#p0.username", condition = "#p0.username != null")
    })
    <S extends User> S save(S entity);

    @Override
    @Cacheable
    Optional<User> findById(Long id);

    @Cacheable
    Optional<User> findByUsername(String username);

    @Cacheable
    List<User> findByNameName(String name);

    default User getById(final Long id) {
        final Optional<User> maybeUser = findById(id);
        return maybeUser.filter(User::isEnabled)
                .orElseThrow(() -> new NotFoundException(User.class, id));
    }
}
