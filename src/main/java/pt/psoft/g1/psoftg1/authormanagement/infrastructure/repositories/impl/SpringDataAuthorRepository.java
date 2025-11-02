package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.AuthorDataModelSQL;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Profile("sqlRedis")
@Repository
public interface SpringDataAuthorRepository extends JpaRepository<AuthorDataModelSQL, Long> {

    Optional<AuthorDataModelSQL> findByAuthorNumber(Long authorNumber);

    List<AuthorDataModelSQL> findByNameStartingWithIgnoreCase(String name);

    List<AuthorDataModelSQL> findByNameIgnoreCase(String name);

    @Query("SELECT a AS author, COUNT(l) AS lendingsCount " +
           "FROM Lending l JOIN l.book b JOIN b.authors a " +
           "GROUP BY a ORDER BY COUNT(l) DESC")
    Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageable);

    @Query("SELECT coAuthor FROM AuthorDataModelSQL a JOIN a.books b JOIN b.authors coAuthor " +
           "WHERE a.authorNumber = :authorNumber AND coAuthor <> a")
    List<AuthorDataModelSQL> findCoAuthorsByAuthorNumber(Long authorNumber);
}
