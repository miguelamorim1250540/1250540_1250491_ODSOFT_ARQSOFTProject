// package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.RepositorySQL;

// import org.springframework.stereotype.Repository;
// import pt.psoft.g1.psoftg1.bookmanagement.model.Title;
// import pt.psoft.g1.psoftg1.bookmanagement.repositories.TitleRepository;
// import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.SpringDataTitleRepository;
// import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mappers.TitleMapper;

// import java.util.Optional;

// @Repository
// public class TitleRepositorySQL implements TitleRepository {

//     private final SpringDataTitleRepository jpaRepo;

//     public TitleRepositorySQL(SpringDataTitleRepository jpaRepo) {
//         this.jpaRepo = jpaRepo;
//     }

//     @Override
//     public Optional<Title> findByValue(String title) {
//         return jpaRepo.findByTitle(title)
//                 .map(TitleMapper::toDomain);
//     }

//     @Override
//     public Title save(Title title) {
//         var dataModel = TitleMapper.toDataModel(title);
//         var saved = jpaRepo.save(dataModel);
//         return TitleMapper.toDomain(saved);
//     }
// }