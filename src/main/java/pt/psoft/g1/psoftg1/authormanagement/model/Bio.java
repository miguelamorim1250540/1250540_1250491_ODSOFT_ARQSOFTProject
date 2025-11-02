package pt.psoft.g1.psoftg1.authormanagement.model;

import jakarta.persistence.Embeddable;
import pt.psoft.g1.psoftg1.shared.model.StringUtilsCustom;

@Embeddable
public class Bio {

    private static final int BIO_MAX_LENGTH = 4096;

    private String bio;

    protected Bio() {
        // construtor vazio exigido pelo JPA
    }

    public Bio(String bio) {
        setBio(bio);
    }

    public void setBio(String bio) {
        if (bio == null)
            throw new IllegalArgumentException("Bio cannot be null");
        if (bio.isBlank())
            throw new IllegalArgumentException("Bio cannot be blank");
        if (bio.length() > BIO_MAX_LENGTH)
            throw new IllegalArgumentException("Bio has a maximum of 4096 characters");
        this.bio = StringUtilsCustom.sanitizeHtml(bio);
    }

    public String getBio() {
        return bio;
    }

    @Override
    public String toString() {
        return bio;
    }
}