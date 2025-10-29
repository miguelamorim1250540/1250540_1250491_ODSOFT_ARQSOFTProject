package pt.psoft.g1.psoftg1.bookmanagement.services;

import pt.psoft.g1.psoftg1.bookmanagement.model.Book;

public interface IsbnLookup {
    Book findBookByIsbn(String isbn);
}
