package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.mapper;

import java.util.List;

import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.mongodb.BookDocument;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.mongodb.DescriptionDocument;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.mongodb.IsbnDocument;
import pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.mongodb.TitleDocument;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;

public class BookDocumentMapper {
    public static BookDocument toBookDocument(Book book) {
        /* TODO: AuthorDocument and GenreDocument
        List<AuthorDocument> authorsDocument = book.getAuthors().stream()
            .map(author -> )
            .toList();
        */
        return new BookDocument(new IsbnDocument(book.getIsbn()),
            new TitleDocument(book.getTitle().toString()),
            new DescriptionDocument(book.getDescription()),
            book.getGenre(),
            null,
            null);
    }

    public static Book toDomain (BookDocument bookDocument) {
        return new Book(bookDocument.getIsbn().getIsbn(),
            bookDocument.getTitle().getTitle(),
            bookDocument.getDescription().getDescription(),
            bookDocument.getGenre(),
            null,
            null);
    }
}
