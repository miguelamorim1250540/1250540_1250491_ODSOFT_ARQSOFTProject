package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.DataModels.ReaderDataModelRedis;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.mappers.ReaderMapperRedis;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderBookCountDTO;
import pt.psoft.g1.psoftg1.readermanagement.services.SearchReadersQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class SpringDataReaderRedisRepository implements ReaderRepository {

    private final CrudRepository<ReaderDataModelRedis, String> redisRepository;
    private final ReaderMapperRedis redisMapper;

    @Override
    public Optional<ReaderDetails> findByReaderNumber(String readerNumber) {
        return redisRepository.findById(readerNumber).map(redisMapper::toDomain);
    }

    @Override
    public List<ReaderDetails> findByPhoneNumber(String phoneNumber) {
        return List.of(); // pesquisa direta no Redis não otimizada
    }

    @Override
    public Optional<ReaderDetails> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<ReaderDetails> findByUserId(Long userId) {
        return Optional.empty();
    }

    @Override
    public int getCountFromCurrentYear() {
        return 0;
    }

    @Override
    public ReaderDetails save(ReaderDetails readerDetails) {
        redisRepository.save(redisMapper.toDataModel(readerDetails));
        return readerDetails;
    }

    @Override
    public Iterable<ReaderDetails> findAll() {
        return StreamSupport.stream(redisRepository.findAll().spliterator(), false)
                .map(redisMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReaderDetails> findTopReaders(Pageable pageable) {
        return Page.empty();
    }

    @Override
    public Page<ReaderBookCountDTO> findTopByGenre(Pageable pageable, String genre, LocalDate startDate, LocalDate endDate) {
        return Page.empty();
    }

    @Override
    public void delete(ReaderDetails readerDetails) {
        redisRepository.deleteById(readerDetails.getReaderNumber());
    }

    @Override
    public List<ReaderDetails> searchReaderDetails(pt.psoft.g1.psoftg1.shared.services.Page page, SearchReadersQuery query) {
        return List.of(); // ainda não implementado
    }
}
