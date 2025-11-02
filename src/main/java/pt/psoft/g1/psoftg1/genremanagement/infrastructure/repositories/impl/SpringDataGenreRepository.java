package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.DataModels.GenreDataModelSQL;

import java.util.List;
import java.util.Optional;

@Profile("sqlRedis")
public interface SpringDataGenreRepository 
        extends CrudRepository<GenreDataModelSQL, Long>, GenreRepoCustom {

    @Query("SELECT g FROM GenreDataModelSQL g")
    List<GenreDataModelSQL> findAllGenres();

    @Query("SELECT g FROM GenreDataModelSQL g WHERE g.genre = :genreName")
    Optional<GenreDataModelSQL> findByString(@Param("genreName") String genreName);

    @Query("SELECT new pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO(g.genre, COUNT(b)) " +
           "FROM GenreDataModelSQL g " +
           "JOIN BookDataModelSQL b ON b.genre.id = g.id " +
           "GROUP BY g.genre " +
           "ORDER BY COUNT(b) DESC")
    Page<GenreBookCountDTO> findTop5GenreByBookCount(Pageable pageable);
}
