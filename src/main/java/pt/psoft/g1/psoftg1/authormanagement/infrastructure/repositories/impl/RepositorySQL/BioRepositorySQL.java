// package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.RepositorySQL;

// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Repository;

// import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.SpringDataBioRepository;
// import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.BioDataModelSQL;
// import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mappers.BioMapper;
// import pt.psoft.g1.psoftg1.authormanagement.model.Bio;
// import pt.psoft.g1.psoftg1.authormanagement.repositories.BioRepository;

// import java.util.Optional;
// import java.util.stream.StreamSupport;

// @Repository
// @RequiredArgsConstructor
// public class BioRepositorySQL implements BioRepository {

//     private final SpringDataBioRepository jpaRepo;

//     @Override
//     public Bio save(Bio bio) {
//         BioDataModelSQL entity = BioMapper.toDataModel(bio);
//         BioDataModelSQL saved = jpaRepo.save(entity);
//         return BioMapper.toDomain(saved);
//     }

//     @Override
//     public Optional<Bio> findById(Long id) {
//         return jpaRepo.findById(id).map(BioMapper::toDomain);
//     }

// @Override
// public Iterable<Bio> findAll() {
//     Iterable<BioDataModelSQL> entities = jpaRepo.findAll();

//     // Converter manualmente o Iterable para Stream
//     return StreamSupport.stream(entities.spliterator(), false)
//             .map(BioMapper::toDomain)
//             .toList();
// }

//     @Override
//     public void delete(Bio bio) {
//         jpaRepo.delete(BioMapper.toDataModel(bio));
//     }
// }
