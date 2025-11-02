package pt.psoft.g1.psoftg1.genremanagement.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.shared.model.AlgorithmId;
import pt.psoft.g1.psoftg1.shared.model.IDGeneratorFactory;

@ExtendWith(MockitoExtension.class)
public class GenreServiceOpaqueTest {
    @Mock private GenreRepository genreRepository;
    @Mock private IDGeneratorFactory idGeneratorFactory;

    @InjectMocks private GenreServiceImpl genreService;

    @Test
    void testCreateGenreSuccess() {
        String genreName = "Fiction";
        AlgorithmId generator = mock(AlgorithmId.class);
        when(idGeneratorFactory.getGenerator("AlgorithmHash")).thenReturn(generator);
        when(generator.generateId(genreName)).thenReturn("GEN-1");
        when(genreRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Genre genre = genreService.create(genreName);

        assertEquals("GEN-1", genre.getId());
        assertEquals("Fiction", genre.getGenre());
    }

    @Test
    void testFindByStringReturnsGenre() {
        Genre genre = new Genre("1", "Fiction");
        when(genreRepository.findByString("Fiction")).thenReturn(Optional.of(genre));

        Optional<Genre> result = genreService.findByString("Fiction");

        assertTrue(result.isPresent());
        assertEquals("Fiction", result.get().getGenre());
    }
}
