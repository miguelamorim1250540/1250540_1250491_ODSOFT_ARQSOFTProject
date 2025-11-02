package pt.psoft.g1.psoftg1.bookmanagement.api.isbnmanagement.services;

import lombok.RequiredArgsConstructor;
import pt.psoft.g1.psoftg1.bookmanagement.api.isbnmanagement.impl.GoogleBooksProvider;
import pt.psoft.g1.psoftg1.bookmanagement.api.isbnmanagement.impl.OpenLibraryProvider;
import pt.psoft.g1.psoftg1.bookmanagement.api.isbnmanagement.infastructure.IsbnProvider;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FallbackIsbnProvider implements IsbnProvider {

    private final GoogleBooksProvider googleBooksProvider;
    private final OpenLibraryProvider openLibraryProvider;

    @Override
    public String getIsbnByTitle(String title) {
        String isbn = googleBooksProvider.getIsbnByTitle(title);
        if (isbn == null) {
            isbn = openLibraryProvider.getIsbnByTitle(title);
        }
        return isbn;
    }
}
