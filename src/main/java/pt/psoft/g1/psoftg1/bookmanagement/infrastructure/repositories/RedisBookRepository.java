package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.redis.BookCache;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;

@Profile({"mongodb-redis", "sql-redis"})
public interface RedisBookRepository extends BookRepository, CrudRepository<BookCache, String>{
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
