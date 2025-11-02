package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.RepositoryRedis;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl.DataModelRedis.BookDataModelRedis;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;

public interface RedisBookRepository extends BookRepository, CrudRepository<BookDataModelRedis, String>{
    Optional<Book> findByIsbn(String isbn);

    @Override
    List<Book> findByGenre(String genre);

    @Override
    List<Book> findByTitle(String title);

    @Override
    List<Book> findByAuthorName(String authorName);

    @Override
    List<Book> findBooksByAuthorNumber(Long authorNumber);
}
