package pt.psoft.g1.psoftg1.bookmanagement.repositories;

import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;

import java.util.Optional;

public interface IsbnRepository {

    Optional<Isbn> findByValue(String isbn);

    Isbn save(Isbn isbn);
}
