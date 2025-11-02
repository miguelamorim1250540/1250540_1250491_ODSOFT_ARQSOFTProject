package pt.psoft.g1.psoftg1.authormanagement.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;
import pt.psoft.g1.psoftg1.authormanagement.services.CreateAuthorRequest;
import pt.psoft.g1.psoftg1.authormanagement.services.UpdateAuthorRequest;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookViewMapper;
import pt.psoft.g1.psoftg1.shared.services.ConcurrencyService;
import pt.psoft.g1.psoftg1.shared.services.FileStorageService;

//SUT - AuthorController + AuthorService

@ExtendWith(MockitoExtension.class)
public class AuthorControllerOpaqueBoxTest {
    @InjectMocks
    private AuthorController authorController;

    @Mock
    private AuthorService authorService;
    @Mock
    private AuthorViewMapper authorViewMapper;
    @Mock
    private ConcurrencyService concurrencyService;
    @Mock
    private FileStorageService fileStorageService;
    @Mock
    private BookViewMapper bookViewMapper;

    private Author author;
    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        author = new Author("", "", "");
        author.setAuthorNumber(1L);
        author.setVersion(1L);
        mockFile = mock(MultipartFile.class);
    }

    @Test
    void createAuthor_success() {
        CreateAuthorRequest request = new CreateAuthorRequest();
        request.setPhoto(mockFile);

        when(fileStorageService.getRequestPhoto(mockFile)).thenReturn("photo.jpg");
        when(authorService.create(any())).thenReturn(author);
        when(authorViewMapper.toAuthorView(author)).thenReturn(new AuthorView());

        ResponseEntity<AuthorView> response = authorController.create(request);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(authorService).create(any());
    }

    @Test
    void findByAuthorNumber_success() {
        when(authorService.findByAuthorNumber(1L)).thenReturn(Optional.of(author));
        when(authorViewMapper.toAuthorView(author)).thenReturn(new AuthorView());

        ResponseEntity<AuthorView> response = authorController.findByAuthorNumber(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void findByAuthorNumber_notFound() {
        when(authorService.findByAuthorNumber(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authorController.findByAuthorNumber(1L));
    }

    @Test
    void partialUpdate_success() {
        UpdateAuthorRequest request = new UpdateAuthorRequest();
        WebRequest webRequest = mock(WebRequest.class);
        when(webRequest.getHeader(anyString())).thenReturn("1");

        when(fileStorageService.getRequestPhoto(mockFile)).thenReturn("photo.jpg");
        when(authorService.partialUpdate(eq(1L), any(), anyLong())).thenReturn(author);
        when(authorViewMapper.toAuthorView(author)).thenReturn(new AuthorView());

        request.setPhoto(mockFile);

        ResponseEntity<AuthorView> response = authorController.partialUpdate(1L, webRequest, request);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(authorService).partialUpdate(eq(1L), any(), anyLong());
    }

    @Test
    void getBooksByAuthorNumber_success() {
        when(authorService.findByAuthorNumber(1L)).thenReturn(Optional.of(author));
        when(authorService.findBooksByAuthorNumber(1L)).thenReturn(Collections.emptyList());
        when(bookViewMapper.toBookView(anyList())).thenReturn(Collections.emptyList());

        var response = authorController.getBooksByAuthorNumber(1L);

        assertNotNull(response);
        assertTrue(response.getItems().isEmpty());
    }

    @Test
    void getTop5Authors_success() {
        when(authorService.findTopAuthorByLendings()).thenReturn(Arrays.asList(new AuthorLendingView()));
        var response = authorController.getTop5();
        assertNotNull(response);
        assertFalse(response.getItems().isEmpty());
    }

    @Test
    void getTop5Authors_notFound() {
        when(authorService.findTopAuthorByLendings()).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> authorController.getTop5());
    }
}
