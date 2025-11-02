package pt.psoft.g1.psoftg1.authormanagement.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.SpringDataAuthorRepository;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.DataModels.AuthorDataModelSQL;
import pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl.mappers.AuthorMapper;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@Profile("sqlRedis")
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {

    private final SpringDataAuthorRepository springDataAuthorRepository;

    @Override
    public Optional<Author> findByAuthorNumber(Long authorNumber) {
        return springDataAuthorRepository.findByAuthorNumber(authorNumber)
                .map(AuthorMapper::toDomain);
    }

    @Override
    public List<Author> searchByNameStartsWith(String name) {
        return springDataAuthorRepository.findByNameStartingWithIgnoreCase(name)
                .stream()
                .map(AuthorMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Author> searchByNameName(String name) {
        return springDataAuthorRepository.findByNameIgnoreCase(name)
                .stream()
                .map(AuthorMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Author save(Author author) {
        AuthorDataModelSQL saved = springDataAuthorRepository.save(AuthorMapper.toDataModel(author));
        return AuthorMapper.toDomain(saved);
    }

    @Override
    public Iterable<Author> getAllAuthors() {
    List<AuthorDataModelSQL> dataModels = springDataAuthorRepository.findAll();
    return dataModels.stream()
            .map(AuthorMapper::toDomain)
            .collect(Collectors.toList());
}

    @Override
    public Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageableRules) {
        return springDataAuthorRepository.findTopAuthorByLendings(pageableRules);
    }

    @Override
    public void delete(Author author) {
        springDataAuthorRepository.delete(AuthorMapper.toDataModel(author));
    }

    @Override
    public List<Author> findCoAuthorsByAuthorNumber(Long authorNumber) {
        return springDataAuthorRepository.findCoAuthorsByAuthorNumber(authorNumber)
                .stream()
                .map(AuthorMapper::toDomain)
                .collect(Collectors.toList());
    }

//     @Override
//     public Iterable<Author> getAllAuthors() {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'getAllAuthors'");
//     }
}
