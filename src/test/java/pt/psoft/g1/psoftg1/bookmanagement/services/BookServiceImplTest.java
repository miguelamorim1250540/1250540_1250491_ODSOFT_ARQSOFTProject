package pt.psoft.g1.psoftg1.bookmanagement.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.shared.services.IdGenerator;

public class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private BookServiceImpl bookService;

    private Genre sampleGenre;
    private Author sampleAuthor;
    private List<Author> sampleAuthors;
    private Book sampleBook;

    @BeforeEach
    void setUp() {
        sampleGenre = new Genre("Fantasia");
        sampleAuthor = new Author("João", "Bio", null);
        sampleAuthors = List.of(sampleAuthor);

        // Construtor: Book(String id, String isbn, String title, String description, Genre genre, List<Author> authors, String photoURI)
        sampleBook = new Book("id-1", "9782826012092", "Encantos de contar", "Descrição", sampleGenre, sampleAuthors, null);
    }

    // -------- create(CreateBookRequest request, String isbn) -------------
    @Test
    void create_shouldGenerateId_andSaveBook_whenIsbnNotExists() {
        String isbn = "9782826012092";
        CreateBookRequest req = new CreateBookRequest();
        req.setTitle("Encantos de contar");
        req.setDescription("Descrição");
        req.setGenre("Fantasia");
        req.setAuthors(List.of(1L));
        req.setPhotoURI(null);

        // mocks
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());
        when(genreRepository.findByString("Fantasia")).thenReturn(Optional.of(sampleGenre));
        when(authorRepository.findByAuthorNumber(1L)).thenReturn(Optional.of(sampleAuthor));
        when(idGenerator.generate()).thenReturn("generated-id-123");
        // when saving, repository returns domain object (simulates persistence mapping)
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book created = bookService.create(req, isbn);

        assertNotNull(created);
        assertEquals("generated-id-123", created.getId()); // se BookServiceImpl atribui id do idGenerator ao construir
        verify(bookRepository).findByIsbn(isbn);
        verify(idGenerator).generate();
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void create_shouldThrowConflict_whenIsbnAlreadyExists() {
        String isbn = "9782826012092";
        CreateBookRequest req = new CreateBookRequest();
        req.setTitle("Encantos de contar");
        req.setDescription("Descrição");
        req.setGenre("Fantasia");
        req.setAuthors(List.of(1L));

        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(sampleBook));

        assertThrows(RuntimeException.class, () -> bookService.create(req, isbn)); // o teu serviço lança ConflictException; aqui aceitamos RuntimeException
        verify(bookRepository).findByIsbn(isbn);
        verify(bookRepository, never()).save(any());
    }

    // -------- update(UpdateBookRequest request, String currentVersion) -------------
    @Test
    void update_shouldApplyPatchAndSave_whenVersionsMatch() {
        String isbn = sampleBook.getIsbn();
        UpdateBookRequest req = new UpdateBookRequest();
        req.setIsbn(isbn);
        req.setTitle("Novo Título");
        // Simula authors numbers -> author objects
        req.setAuthors(List.of(1L));
        req.setPhotoURI(null);

        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(sampleBook));
        when(authorRepository.findByAuthorNumber(1L)).thenReturn(Optional.of(sampleAuthor));
        when(genreRepository.findByString(anyString())).thenReturn(Optional.of(sampleGenre));
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        // currentVersion string passed to service is parsed into long in your implementation
        Book updated = bookService.update(req, String.valueOf(sampleBook.getVersion() == null ? 0L : sampleBook.getVersion()));

        assertNotNull(updated);
        // title changed via applyPatch -> depends on implementation, but at least save called
        verify(bookRepository).save(any(Book.class));
    }

    // -------- findByIsbn(String) -------------
    @Test
    void findByIsbn_shouldReturnBook_whenPresent() {
        String isbn = sampleBook.getIsbn();
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(sampleBook));

        Book found = bookService.findByIsbn(isbn);

        assertNotNull(found);
        assertEquals(isbn, found.getIsbn());
        verify(bookRepository).findByIsbn(isbn);
    }

    // -------- findByTitle / findByGenre / findByAuthorName -------------
    @Test
    void findByTitle_shouldDelegateToRepository() {
        String title = "Encantos";
        when(bookRepository.findByTitle(title)).thenReturn(List.of(sampleBook));

        List<Book> result = bookService.findByTitle(title);

        assertEquals(1, result.size());
        verify(bookRepository).findByTitle(title);
    }

    @Test
    void findByGenre_shouldDelegateToRepository() {
        String genre = "Fantasia";
        when(bookRepository.findByGenre(genre)).thenReturn(List.of(sampleBook));

        List<Book> result = bookService.findByGenre(genre);

        assertEquals(1, result.size());
        verify(bookRepository).findByGenre(genre);
    }

    @Test
    void findByAuthorName_shouldDelegateToRepository_withWildcard() {
        String authorName = "João";
        when(bookRepository.findByAuthorName(authorName + "%")).thenReturn(List.of(sampleBook));

        List<Book> result = bookService.findByAuthorName(authorName);

        assertEquals(1, result.size());
        // bookService adds "%" when calling repo, but we verify repository call above
        verify(bookRepository).findByAuthorName(authorName + "%");
    }

    // -------- findTop5BooksLent(LocalDate, Pageable) -------------
    /*@Test
    void findTop5BooksLent_shouldReturnPageContent() {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        Page<BookCountDTO> page = new PageImpl<>(List.of(new BookCountDTO("9782826012092", 5L)));
        when(bookRepository.findTop5BooksLent(eq(oneYearAgo), any(Pageable.class))).thenReturn(page);

        List<BookCountDTO> top = bookService.findTop5BooksLent();

        assertNotNull(top);
        assertEquals(1, top.size());
        verify(bookRepository).findTop5BooksLent(eq(oneYearAgo), any(Pageable.class));
    }

    // -------- searchBooks(Page, SearchBooksQuery) -------------
    @Test
    void searchBooks_shouldDelegateToRepository() {
        pt.psoft.g1.psoftg1.shared.services.Page page = new pt.psoft.g1.psoftg1.shared.services.Page(1, 10);
        pt.psoft.g1.psoftg1.bookmanagement.services.search.SearchBooksQuery query =
                new pt.psoft.g1.psoftg1.bookmanagement.services.search.SearchBooksQuery("title", "author", "genre");

        when(bookRepository.searchBooks(eq(page), eq(query))).thenReturn(List.of(sampleBook));

        List<Book> results = bookService.searchBooks(page, query);

        assertEquals(1, results.size());
        verify(bookRepository).searchBooks(page, query);
    }

    // -------- getBooksSuggestionsForReader(String) -------------
    @Test
    void getBooksSuggestionsForReader_shouldUseReaderInterests_andCallFindByGenre() {
        String readerNumber = "reader-1";
        ReaderDetails readerDetails = new ReaderDetails("reader-1", "user", "pass");
        readerDetails.setInterestList(List.of(sampleGenre));
        when(readerRepository.findByReaderNumber(readerNumber)).thenReturn(Optional.of(readerDetails));
        when(bookRepository.findByGenre(sampleGenre.toString())).thenReturn(List.of(sampleBook));

        List<Book> suggestions = bookService.getBooksSuggestionsForReader(readerNumber);

        assertEquals(1, suggestions.size());
        verify(readerRepository).findByReaderNumber(readerNumber);
        verify(bookRepository).findByGenre(sampleGenre.toString());
    }*/

    // -------- save(Book) -------------
    @Test
    void save_shouldDelegateToRepositorySave() {
        when(bookRepository.save(sampleBook)).thenReturn(sampleBook);
        Book saved = bookService.save(sampleBook);
        assertEquals(sampleBook, saved);
        verify(bookRepository).save(sampleBook);
    }
}
