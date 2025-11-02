package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelSQL.DescriptionDataModelSQL;

@Profile("sqlRedis")
@Repository
public interface SpringDataDescriptionRepository extends JpaRepository<DescriptionDataModelSQL, Long> {
}
