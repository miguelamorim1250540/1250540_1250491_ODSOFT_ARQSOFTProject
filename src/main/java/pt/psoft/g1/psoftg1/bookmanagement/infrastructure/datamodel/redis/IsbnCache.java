package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.redis;

import java.io.Serializable;

import lombok.Getter;

public class IsbnCache implements Serializable {
    @Getter
    String isbn;

    public IsbnCache(String isbn) {
        this.isbn = isbn;
    }
}
