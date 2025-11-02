package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.BioDataModelSQL;

@Profile("sqlRedis")
@Repository
public interface SpringDataBioRepository extends CrudRepository<BioDataModelSQL, Long> {
}
