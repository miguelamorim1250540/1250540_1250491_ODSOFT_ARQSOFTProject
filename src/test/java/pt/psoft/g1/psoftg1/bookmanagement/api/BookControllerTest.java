package pt.psoft.g1.psoftg1.bookmanagement.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookService;
import pt.psoft.g1.psoftg1.bookmanagement.services.SearchBooksQuery;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.lendingmanagement.services.LendingService;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderService;
import pt.psoft.g1.psoftg1.shared.services.ConcurrencyService;
import pt.psoft.g1.psoftg1.shared.services.FileStorageService;
import pt.psoft.g1.psoftg1.usermanagement.services.UserService;

public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // controller dependencies (mocked)
    @MockBean private BookService bookService;
    @MockBean private LendingService lendingService;
    @MockBean private ConcurrencyService concurrencyService;
    @MockBean private FileStorageService fileStorageService;
    @MockBean private UserService userService;
    @MockBean private ReaderService readerService;
    @MockBean private BookViewMapper bookViewMapper;

    // sample domain + view objects used across tests
    final Genre sampleGenre = new Genre("Fantasia");
    final Author sampleAuthor = new Author("João", "Bio", null);
    final Book sampleBook = new Book("id-1", "9782826012092", "Encantos de contar", "Descrição", sampleGenre, List.of(sampleAuthor), null);
    final BookView sampleBookView = new BookView();
    {
        sampleBookView.setTitle(sampleBook.getTitle().toString());
        sampleBookView.setIsbn(sampleBook.getIsbn());
        sampleBookView.setGenre(sampleBook.getGenre().getGenre());
        sampleBookView.setDescription(sampleBook.getDescription());
        sampleBookView.setAuthors(List.of(sampleAuthor.getName()));
    }

    // --- GET /api/books/{isbn} ---
    @Test
    @DisplayName("GET /api/books/{isbn} -> 200 + book view")
    void getByIsbn_returnsBookView() throws Exception {
        when(bookService.findByIsbn("9782826012092")).thenReturn(sampleBook);
        when(bookViewMapper.toBookView(sampleBook)).thenReturn(sampleBookView);

        mockMvc.perform(get("/api/books/9782826012092")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                // assert minimal JSON content — ajustar conforme BookView fields reais
                .andExpect(jsonPath("$.isbn").value("9782826012092"))
                .andExpect(jsonPath("$.title").value("Encantos de contar"))
                .andExpect(jsonPath("$.genre").value("Fantasia"));

        verify(bookService, times(1)).findByIsbn("9782826012092");
        verify(bookViewMapper, times(1)).toBookView(sampleBook);
    }

    // --- GET /api/books?title=...  (delegates to service.findByTitle) ---
    @Test
    @DisplayName("GET /api/books?title=... -> delegates to service.findByTitle")
    void findBooks_byTitle_delegatesToService() throws Exception {
        when(bookService.findByTitle("Encantos")).thenReturn(List.of(sampleBook));
        when(bookViewMapper.toBookView(anyList())).thenReturn(List.of(sampleBookView));

        mockMvc.perform(get("/api/books")
                        .param("title", "Encantos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookService).findByTitle("Encantos");
        verify(bookViewMapper).toBookView(anyList());
    }

    // --- GET /api/books/top5 ---
    /*@Test
    @DisplayName("GET /api/books/top5 -> returns top5 list")
    void getTop5BooksLent_delegatesToService() throws Exception {
        // prepare a minimal BookCountView list
        BookCountView bc = BookCountView.builder().isbn("9782826012092").count(5L).build();
        when(bookService.findTop5BooksLent()).thenReturn(List.of());
        // bookViewMapper will be used by controller to convert DTOs; we at least verify controller calls service
        mockMvc.perform(get("/api/books/top5")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookService).findTop5BooksLent();
    }

    // --- POST /api/books/search ---
    @Test
    @DisplayName("POST /api/books/search -> delegates to service.searchBooks and returns 200")
    void postSearch_delegatesToService() throws Exception {
        // build simple search request JSON: { "page": { "page": 1, "size": 10 }, "query": {"title":"x","author":"y","genre":"z"} }
        String body = """
                {
                  "page": { "page": 1, "size": 10 },
                  "query": { "title": "Encantos", "author": "João", "genre": "Fantasia" }
                }
                """;

        when(bookService.searchBooks(any(Page.class), any(SearchBooksQuery.class))).thenReturn(List.of(sampleBook));
        when(bookViewMapper.toBookView(anyList())).thenReturn(List.of(sampleBookView));

        mockMvc.perform(post("/api/books/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        verify(bookService).searchBooks(any(Page.class), any(SearchBooksQuery.class));
        verify(bookViewMapper).toBookView(anyList());
    }*/

    // --- GET /api/books/{isbn}/avgDuration ---
    @Test
@DisplayName("GET /api/books/{isbn}/avgDuration -> returns avg duration view")
void getAvgDuration_returnsView() throws Exception {
    when(bookService.findByIsbn("9782826012092")).thenReturn(sampleBook);
    when(lendingService.getAvgLendingDurationByIsbn("9782826012092")).thenReturn(12.5);

    // Corrigido: criar a view usando BookView e averageLendingDuration
    BookAverageLendingDurationView avgView = new BookAverageLendingDurationView();
    avgView.setBook(sampleBookView);
    avgView.setAverageLendingDuration(12.5);

    when(bookViewMapper.toBookAverageLendingDurationView(eq(sampleBook), eq(12.5)))
            .thenReturn(avgView);

    mockMvc.perform(get("/api/books/9782826012092/avgDuration")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.book.isbn").value("9782826012092"))
            .andExpect(jsonPath("$.book.title").value("Encantos de contar"))
            .andExpect(jsonPath("$.averageLendingDuration").value(12.5));

    verify(bookService).findByIsbn("9782826012092");
    verify(lendingService).getAvgLendingDurationByIsbn("9782826012092");
    verify(bookViewMapper).toBookAverageLendingDurationView(eq(sampleBook), eq(12.5));
}

    // --- GET /api/books/suggestions ---
    /*@Test
    @DisplayName("GET /api/books/suggestions -> uses authenticated user and reader interests")
    void getSuggestions_usesUserAndReader() throws Exception {
        // prepare mocks: userService.getAuthenticatedUser(authentication) -> some User
        // readerService.findByUsername(...) -> ReaderDetails with interest list
        // bookService.getBooksSuggestionsForReader(readerNumber) -> List<Book>
        // bookViewMapper.toBookView(list) -> List<BookView>

        // minimal stubs for user & reader (types are project-specific; here we only mock calls)
        Object fakeUser = new Object(); // we don't inspect user fields in controller test
        ReaderDetails readerDetails = new ReaderDetails("reader-1", "username", "pwd");
        readerDetails.setInterestList(List.of(sampleGenre));

        when(userService.getAuthenticatedUser(any(Authentication.class))).thenReturn((pt.psoft.g1.psoftg1.usermanagement.domain.User) Mockito.mock(pt.psoft.g1.psoftg1.usermanagement.domain.User.class));
        when(readerService.findByUsername(anyString())).thenReturn(Optional.of(readerDetails));
        when(bookService.getBooksSuggestionsForReader("reader-1")).thenReturn(List.of(sampleBook));
        when(bookViewMapper.toBookView(anyList())).thenReturn(List.of(sampleBookView));

        mockMvc.perform(get("/api/books/suggestions")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).getAuthenticatedUser(any());
        verify(readerService).findByUsername(anyString());
        verify(bookService).getBooksSuggestionsForReader("reader-1");
        verify(bookViewMapper).toBookView(anyList());
    }*/
}
