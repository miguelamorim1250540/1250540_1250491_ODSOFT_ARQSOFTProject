// package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.RepositorySQL;

// import org.springframework.stereotype.Repository;
// import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
// import pt.psoft.g1.psoftg1.bookmanagement.repositories.IsbnRepository;
// import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.SpringDataIsbnRepository;
// import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mappers.IsbnMapper;
// import java.util.Optional;

// @Repository
// public class IsbnRepositorySQL implements IsbnRepository {

//     private final SpringDataIsbnRepository jpaRepo;

//     public IsbnRepositorySQL(SpringDataIsbnRepository jpaRepo) {
//         this.jpaRepo = jpaRepo;
//     }

//     @Override
//     public Optional<Isbn> findByValue(String isbn) {
//         return jpaRepo.findByIsbn(isbn)
//                 .map(IsbnMapper::toDomain);
//     }

//     @Override
//     public Isbn save(Isbn isbn) {
//         var dataModel = IsbnMapper.toDataModel(isbn);
//         var saved = jpaRepo.save(dataModel);
//         return IsbnMapper.toDomain(saved);
//     }
// }
