// package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.RepositorySQL;

// import lombok.RequiredArgsConstructor;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.stereotype.Repository;
// import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelSQL.BookDataModelSQL;
// import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.SpringDataBookRepository;
// import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.mappers.BookMapper;
// import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
// import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
// import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;

// import java.time.LocalDate;
// import java.util.List;
// import java.util.Optional;

// @Repository
// @RequiredArgsConstructor
// public class BookRepositorySQL implements BookRepository {

//     private final SpringDataBookRepository jpaRepo;

//     @Override
//     public Optional<Book> findByIsbn(String isbn) {
//         return jpaRepo.findByIsbn(isbn)
//                 .map(BookMapper::toDomain);
//     }

//     @Override
//     public List<Book> findByGenre(String genre) {
//         return jpaRepo.findByGenre(genre).stream()
//                 .map(BookMapper::toDomain)
//                 .toList();
//     }

//     @Override
//     public List<Book> findByTitle(String title) {
//         return jpaRepo.findByTitle(title).stream()
//                 .map(BookMapper::toDomain)
//                 .toList();
//     }

//     @Override
//     public List<Book> findByAuthorName(String authorName) {
//         return jpaRepo.findByAuthorName(authorName).stream()
//                 .map(BookMapper::toDomain)
//                 .toList();
//     }

//     @Override
//     public List<Book> findBooksByAuthorNumber(Long authorNumber) {
//         return jpaRepo.findBooksByAuthorNumber(authorNumber).stream()
//                 .map(BookMapper::toDomain)
//                 .toList();
//     }

//     @Override
//     public Page<BookCountDTO> findTop5BooksLent(LocalDate oneYearAgo, Pageable pageable) {
//         return jpaRepo.findTop5BooksLent(oneYearAgo, pageable);
//     }

//     @Override
//     public Book save(Book book) {
//         BookDataModelSQL entity = BookMapper.toDataModel(book);
//         BookDataModelSQL saved = jpaRepo.save(entity);
//         return BookMapper.toDomain(saved);
//     }

//     // ✅ Remove o @Override daqui
//     public List<Book> findAll() {
//         return jpaRepo.findAll().stream()
//                 .map(BookMapper::toDomain)
//                 .toList();
//     }

//     @Override
//     public void delete(Book book) {
//         jpaRepo.delete(BookMapper.toDataModel(book));
//     }

//     @Override
//     public List<Book> searchBooks(pt.psoft.g1.psoftg1.shared.services.Page page,
//                                   pt.psoft.g1.psoftg1.bookmanagement.services.SearchBooksQuery query) {
//         return List.of(); // podes implementar mais tarde
//     }
// }
