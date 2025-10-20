package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.mongodb.BookDocument;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookCountDTO;

public interface MongoDBBookRepository extends BookRepository, MongoRepository<BookDocument, String>{
    @Query("{ 'isbn': ?0 }")
    Optional<Book> findByIsbn(String isbn);

    @Override
    Page<BookCountDTO> findTop5BooksLent(LocalDate oneYearAgo, Pageable pageable);


    @Override
    @Query("{ 'genre.genre': { $regex: ?0, $options: 'i' } }")
    List<Book> findByGenre(String genre);

    @Override
    @Query("{ 'title.title': { $regex: ?0, $options: 'i' } }")
    List<Book> findByTitle(String title);

    @Override
    @Query("{ 'authors.name.name': { $regex: ?0, $options: 'i' } }")
    List<Book> findByAuthorName(String authorName);

    @Override
    @Query("{ 'authors.authorNumber': ?0 }")
    List<Book> findBooksByAuthorNumber(Long authorNumber);
}
