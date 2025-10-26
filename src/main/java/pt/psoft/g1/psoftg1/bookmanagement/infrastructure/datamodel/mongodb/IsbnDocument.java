package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.mongodb;

import lombok.Getter;

public class IsbnDocument {
    @Getter
    String isbn;

    public IsbnDocument(String isbn) {
        this.isbn = isbn;
    }
}
