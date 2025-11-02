package pt.psoft.g1.psoftg1.bookmanagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import pt.psoft.g1.psoftg1.bookmanagement.api.BookController;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookView;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookViewMapper;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookService;
import pt.psoft.g1.psoftg1.bookmanagement.services.CreateBookRequest;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.lendingmanagement.services.LendingService;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderService;
import pt.psoft.g1.psoftg1.shared.services.ConcurrencyService;
import pt.psoft.g1.psoftg1.shared.services.FileStorageService;
import pt.psoft.g1.psoftg1.usermanagement.services.UserService;

//SUT - BookController + BookService

@ExtendWith(MockitoExtension.class)
public class BookControllerOpaqueBoxTest {
    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;
    @Mock
    private LendingService lendingService;
    @Mock
    private UserService userService;
    @Mock
    private ReaderService readerService;
    @Mock
    private ConcurrencyService concurrencyService;
    @Mock
    private FileStorageService fileStorageService;
    @Mock
    private BookViewMapper bookViewMapper;

    private Book book;
    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        book = new Book("1234567890","Title", "Teste", new Genre("id1", "Teste"), null, "photo1.jpg");
        book.setVersion(1L);
        mockFile = mock(MultipartFile.class);
    }

    @Test
    void createBook_success() {
        CreateBookRequest request = new CreateBookRequest();
        request.setPhoto(mockFile);

        when(fileStorageService.getRequestPhoto(mockFile)).thenReturn("photo.jpg");
        when(bookService.create(any())).thenReturn(book);
        when(bookViewMapper.toBookView(book)).thenReturn(new BookView());

        ResponseEntity<BookView> response = bookController.create(request, "1234567890");

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(bookService).create(any());
    }

    @Test
    void findByIsbn_success() {
        when(bookService.findByIsbn("1234567890")).thenReturn(book);
        when(bookViewMapper.toBookView(book)).thenReturn(new BookView());

        ResponseEntity<BookView> response = bookController.findByIsbn("1234567890");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void updateBook_success() {
        UpdateBookRequest request = new UpdateBookRequest();
        WebRequest webRequest = mock(WebRequest.class);
        when(webRequest.getHeader(anyString())).thenReturn("1");
        when(fileStorageService.getRequestPhoto(mockFile)).thenReturn("photo.jpg");
        when(bookService.update(any(), anyString())).thenReturn(book);
        when(bookViewMapper.toBookView(book)).thenReturn(new BookView());

        request.setPhoto(mockFile);

        ResponseEntity<BookView> response = bookController.updateBook("1234567890", webRequest, request);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }
}
