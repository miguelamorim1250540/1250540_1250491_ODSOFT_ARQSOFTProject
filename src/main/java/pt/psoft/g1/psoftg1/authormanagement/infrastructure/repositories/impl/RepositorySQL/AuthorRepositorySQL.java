package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.RepositorySQL;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.SpringDataAuthorRepository;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.AuthorDataModelSQL;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositorySQL implements AuthorRepository {

    private final SpringDataAuthorRepository jpaRepo;

    @Override
    public Optional<Author> findByAuthorNumber(Long authorNumber) {
        return jpaRepo.findByAuthorNumber(authorNumber)
                .map(AuthorDataModelSQL::toDomain);
    }

    @Override
    public List<Author> searchByNameStartsWith(String name) {
        return jpaRepo.findByNameStartingWithIgnoreCase(name).stream()
                .map(AuthorDataModelSQL::toDomain)
                .toList();
    }

    @Override
    public List<Author> searchByNameName(String name) {
        return jpaRepo.findByNameIgnoreCase(name).stream()
                .map(AuthorDataModelSQL::toDomain)
                .toList();
    }

    @Override
    public Author save(Author author) {
        AuthorDataModelSQL entity = new AuthorDataModelSQL(author);
        AuthorDataModelSQL saved = jpaRepo.save(entity);
        return saved.toDomain();
    }

    @Override
    public Iterable<Author> findAll() {
        return jpaRepo.findAll().stream()
                .map(AuthorDataModelSQL::toDomain)
                .toList();
    }

    @Override
    public void delete(Author author) {
        jpaRepo.delete(new AuthorDataModelSQL(author));
    }

    @Override
    public List<Author> findCoAuthorsByAuthorNumber(Long authorNumber) {
        return jpaRepo.findCoAuthorsByAuthorNumber(authorNumber).stream()
                .map(AuthorDataModelSQL::toDomain)
                .toList();
    }

    @Override
    public Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageableRules) {
        return jpaRepo.findTopAuthorByLendings(pageableRules);
    }
}
