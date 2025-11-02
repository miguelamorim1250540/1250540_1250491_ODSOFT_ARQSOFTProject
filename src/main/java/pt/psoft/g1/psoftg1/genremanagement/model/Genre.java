package pt.psoft.g1.psoftg1.genremanagement.model;

import lombok.Getter;

public class Genre {

    private static final int GENRE_MAX_LENGTH = 100;

    @Getter
    private String id; // 🔹 Novo campo gerado automaticamente
    @Getter
    private String genre;

    public Genre(String id, String genre) {
        this.id = id;
        this.genre = "";
        setGenre(genre);
    }

    private void setGenre(String genre) {
        if (genre == null)
            throw new IllegalArgumentException("Genre cannot be null");
        if (genre.isBlank())
            throw new IllegalArgumentException("Genre cannot be blank");
        if (genre.length() > GENRE_MAX_LENGTH)
            throw new IllegalArgumentException("Genre has a maximum of " + GENRE_MAX_LENGTH + " characters");
        this.genre = genre;
    }

    @Override
    public String toString() {
        return genre;
    }
}
