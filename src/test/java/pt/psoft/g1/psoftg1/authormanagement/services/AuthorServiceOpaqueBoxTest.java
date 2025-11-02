package pt.psoft.g1.psoftg1.authormanagement.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.shared.model.IDGeneratorFactory;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceOpaqueBoxTest {
    @Mock private AuthorRepository authorRepository;
    @Mock private BookRepository bookRepository;
    @Mock private AuthorMapper mapper;
    @Mock private PhotoRepository photoRepository;
    @Mock private IDGeneratorFactory idGeneratorFactory;

    @InjectMocks private AuthorServiceImpl authorService;

    @Test
    void testFindAllReturnsAuthors() {
        List<Author> authors = List.of(new Author("João", "teste", "photo1.jpg"));
        when(authorRepository.getAllAuthors()).thenReturn(authors);

        Iterable<Author> result = authorService.findAll();

        assertIterableEquals(authors, result);
    }

    @Test
    void testFindByAuthorNumberReturnsOptional() {
        Author author = new Author("João", "teste", "photo1.jpg");
        when(authorRepository.findByAuthorNumber(1L)).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.findByAuthorNumber(1L);

        assertTrue(result.isPresent());
        assertEquals("João", result.get().getName());
    }

    @Test
    void testCreateAuthorSuccess() {
        CreateAuthorRequest request = new CreateAuthorRequest();
        request.setName("Joana");
        request.setBio("teste1");
        request.setPhoto(null);
        request.setPhotoURI(null);

        when(idGeneratorFactory.generateIdUsing(anyString(), anyString())).thenReturn("GEN-1");
        when(authorRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Author created = authorService.create(request);

        assertEquals("GEN-1", created.getAuthorNumber());
        assertEquals("Joana", created.getName());
    }

    @Test
    void testPartialUpdateThrowsNotFound() {
        UpdateAuthorRequest request = new UpdateAuthorRequest();
        when(authorRepository.findByAuthorNumber(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> 
            authorService.partialUpdate(1L, request, 0)
        );
    }
}
