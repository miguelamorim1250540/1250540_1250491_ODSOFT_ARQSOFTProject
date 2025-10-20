package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.mapper;

import java.util.List;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.mongodb.BookDocument;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

public class BookDocumentMapper {
    public static BookDocument toBookDocument(Book book) {
        List<String> authorsIdList = book.getAuthors().stream()
            .map(author -> String.valueOf(author.getId()))
            .toList();

        BookDocument bookDocument = new BookDocument(book.getIsbn(), book.getTitle().toString(), book.getDescription(), book.getGenre().toString(), authorsIdList, "");
        
        return bookDocument;
    }

    public static Book toDomain (BookDocument bookDocument, Genre genre, List<Author> authors) {
        Book book = new Book(bookDocument.getIsbn(), bookDocument.getTitle(), bookDocument.getDescription(), genre, authors, bookDocument.getPhotoUrl());

        return book;
    }
}
