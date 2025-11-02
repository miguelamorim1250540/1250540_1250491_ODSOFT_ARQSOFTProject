package pt.psoft.g1.psoftg1.bookmanagement.api.isbnmanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Value;
import pt.psoft.g1.psoftg1.bookmanagement.api.isbnmanagement.impl.GoogleBooksProvider;
import pt.psoft.g1.psoftg1.bookmanagement.api.isbnmanagement.impl.OpenLibraryProvider;
import pt.psoft.g1.psoftg1.bookmanagement.api.isbnmanagement.infastructure.IsbnProvider;

@Component
public class IsbnProviderFactory {

    private final GoogleBooksProvider googleBooksProvider;

    private final OpenLibraryProvider openLibraryProvider;

    private final FallbackIsbnProvider fallbackIsbnProvider;

    @Value("${isbn.provider}")
    private String activeProvider;

    @Autowired
    public IsbnProviderFactory(GoogleBooksProvider googleBooksProvider, OpenLibraryProvider openLibraryProvider, FallbackIsbnProvider fallbackIsbnProvider) {
        this.googleBooksProvider = googleBooksProvider;
        this.openLibraryProvider = openLibraryProvider;
        this.fallbackIsbnProvider = fallbackIsbnProvider;
    }

    public IsbnProvider getProvider() {
        return switch (activeProvider.toLowerCase()) {
            case "googlebooks" -> googleBooksProvider;
            case "openlibrary" -> openLibraryProvider;
            case "gb_and_ol", "fallback" -> fallbackIsbnProvider;
            default -> fallbackIsbnProvider; // fallback
        };
    }
}