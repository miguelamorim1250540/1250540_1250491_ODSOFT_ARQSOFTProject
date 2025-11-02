// package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.RepositorySQL;

// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Repository;
// import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.SpringDataDescriptionRepository;
// import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelSQL.DescriptionDataModelSQL;
// import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mappers.DescriptionMapper;
// import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
// import pt.psoft.g1.psoftg1.bookmanagement.repositories.DescriptionRepository;

// @Repository
// @RequiredArgsConstructor
// public class DescriptionRepositorySQL implements DescriptionRepository {

//     private final SpringDataDescriptionRepository jpaRepo;

//     @Override
//     public Description save(Description description) {
//         DescriptionDataModelSQL dataModel = DescriptionMapper.toDataModel(description);
//         DescriptionDataModelSQL saved = jpaRepo.save(dataModel);
//         return DescriptionMapper.toDomain(saved);
//     }
// }
