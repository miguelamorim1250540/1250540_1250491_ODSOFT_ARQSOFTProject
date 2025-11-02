package pt.psoft.g1.psoftg1.bookmanagement.repositories;

import pt.psoft.g1.psoftg1.bookmanagement.model.Title;

import java.util.Optional;

public interface TitleRepository {
    Optional<Title> findByValue(String title);
    Title save(Title title);
}
