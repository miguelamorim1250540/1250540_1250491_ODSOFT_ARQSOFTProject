package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

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

@Repository
public interface SpringDataAuthorRepository extends JpaRepository<AuthorDataModelSQL, Long> {

    Optional<AuthorDataModelSQL> findByAuthorNumber(Long authorNumber);

    List<AuthorDataModelSQL> findByNameStartingWithIgnoreCase(String name);

    List<AuthorDataModelSQL> findByNameIgnoreCase(String name);

    @Query("SELECT null") // apenas placeholder temporário
    Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageable);


    // @Query("""
    //         SELECT new pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView(a.name, COUNT(l.pk))
    //         FROM Book b
    //         JOIN b.authors a
    //         JOIN Lending l ON l.book.pk = b.pk
    //         GROUP BY a.name
    //         ORDER BY COUNT(l) DESC
    //         """)
    // Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageable);

    // @Query("""
    //         SELECT DISTINCT coAuthor
    //         FROM Book b
    //         JOIN b.authors coAuthor
    //         WHERE b IN (
    //             SELECT b2 FROM Book b2
    //             JOIN b2.authors a
    //             WHERE a.authorNumber = :authorNumber
    //         )
    //         AND coAuthor.authorNumber <> :authorNumber
    //         """)
    List<AuthorDataModelSQL> findCoAuthorsByAuthorNumber(Long authorNumber);
}
