package pt.psoft.g1.psoftg1.authormanagement.model;

import org.hibernate.StaleObjectStateException;

import pt.psoft.g1.psoftg1.authormanagement.services.UpdateAuthorRequest;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.shared.model.EntityWithPhoto;
import pt.psoft.g1.psoftg1.shared.model.Name;
import jakarta.persistence.Embedded;

public class Author extends EntityWithPhoto {

    private String authorNumber;
    private long version;
    private Name name;

    @Embedded
    private Bio bio;

    public void setName(String name) {
        this.name = new Name(name);
    }

    public void setBio(String bio) {
        this.bio = new Bio(bio);
    }

    public Long getVersion() {
        return version;
    }

    public Author(String name, String bio, String photoURI) {
        setName(name);
        setBio(bio);
        setPhotoInternal(photoURI);
    }

    protected Author() {
        // for ORM only
    }
    
    public String getAuthorNumber() {
        return authorNumber;
    }

    public void setAuthorNumber(String authorNumber) {
        this.authorNumber = authorNumber;
    }

    
    public void applyPatch(final long desiredVersion, final UpdateAuthorRequest request) {
        if (this.version != desiredVersion)
            throw new StaleObjectStateException("Object was already modified by another user", this.authorNumber);

        if (request.getName() != null)
            setName(request.getName());

        if (request.getBio() != null)
            setBio(request.getBio());

        if (request.getPhotoURI() != null)
            setPhotoInternal(request.getPhotoURI());
    }

    public void removePhoto(long desiredVersion) {
        if (desiredVersion != this.version) {
            throw new ConflictException("Provided version does not match latest version of this object");
        }

        setPhotoInternal(null);
    }

    public String getName() {
        return this.name.toString();
    }

    public String getBio() {
        return this.bio != null ? this.bio.toString() : null;
    }

    public String getPhotoInternal() {
        return (this.photo != null) ? this.photo.toString() : null;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public void setAuthorNumber(Long authorNumber2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setAuthorNumber'");
    }
}