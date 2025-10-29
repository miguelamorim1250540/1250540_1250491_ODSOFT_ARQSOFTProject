package pt.psoft.g1.psoftg1.external.openlibraryapi;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.services.IsbnLookup;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.external.openlibraryapi.dto.OpenLibraryAPIResponse;

@Profile("open-library-search")
public class IsbnLookupImpl implements IsbnLookup {
    private static final String BASE_URL = "https://openlibrary.org/search.json";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Book findBookByIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) 
            throw new IllegalArgumentException("ISBN não pode ser nulo ou vazio");

        String uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                        .queryParam("q", isbn).toUriString();
        OpenLibraryAPIResponse response = restTemplate.getForObject(uri, OpenLibraryAPIResponse.class);

        if (response == null || response.getDocs().isEmpty()) {
            return null;
        }

        OpenLibraryAPIResponse.Doc doc = response.getDocs().get(0);

        List<Author> authors = doc.getAuthor_name() != null
                ? doc.getAuthor_name().stream()
                    .map(name -> new Author(name, "", null))
                    .collect(Collectors.toList())
                : List.of();

        return new Book(
                null,           
                isbn,           
                doc.getTitle(), 
                "Unknown",    
                new Genre("Unknown"), 
                authors,        
                null        
        );
    }
}
