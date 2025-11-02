package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl.DataModel.UserDataModelSQL;

import java.util.Optional;
import java.util.List;

@Repository
public interface SpringDataUserRepository extends JpaRepository<UserDataModelSQL, Long> {
    Optional<UserDataModelSQL> findByUsername(String username);
    List<UserDataModelSQL> findByNameName(String name);
    List<UserDataModelSQL> findByNameNameContains(String name);
}
