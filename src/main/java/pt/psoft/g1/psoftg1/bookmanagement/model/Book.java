package pt.psoft.g1.psoftg1.bookmanagement.model;

import lombok.Getter;
import org.hibernate.StaleObjectStateException;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.shared.model.EntityWithPhoto;

import java.util.List;
import java.util.Objects;

@Getter
public class Book extends EntityWithPhoto {

    private long id;
    private Long version;
    private Isbn isbn;
    private Title title;
    private Genre genre;
    private Description description;
    private List<Author> authors;

    public Book(String isbn, String title, String description, Genre genre, List<Author> authors, String photoURI) {
        if (genre == null)
            throw new IllegalArgumentException("Genre cannot be null");
        if (authors == null || authors.isEmpty())
            throw new IllegalArgumentException("Author list is null or empty");

        this.isbn = new Isbn(isbn);
        this.title = new Title(title);
        this.description = description != null ? new Description(description) : null;
        this.genre = genre;
        this.authors = authors;
        setPhotoInternal(photoURI);
    }

    protected Book() {
        // for ORM
    }

    public void removePhoto(long desiredVersion) {
        if (!Objects.equals(this.version, desiredVersion)) {
            throw new ConflictException("Provided version does not match latest version of this object");
        }
        setPhotoInternal(null);
    }

    public void applyPatch(final Long desiredVersion, UpdateBookRequest request) {
        if (!Objects.equals(this.version, desiredVersion))
            throw new StaleObjectStateException("Object was already modified by another user", this.id);

        if (request.getTitle() != null)
            this.title = new Title(request.getTitle());

        if (request.getDescription() != null)
            this.description = new Description(request.getDescription());

        if (request.getGenreObj() != null)
            this.genre = request.getGenreObj();

        if (request.getAuthorObjList() != null)
            this.authors = request.getAuthorObjList();

        if (request.getPhotoURI() != null)
            setPhotoInternal(request.getPhotoURI());
    }

    public String getIsbn() {
        return this.isbn.toString();
    }

    public String getDescription() {
        return this.description != null ? this.description.toString() : "";
    }
}
