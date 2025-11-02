package pt.psoft.g1.psoftg1.shared.model;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;

import lombok.Getter;

@Getter
public abstract class EntityWithPhoto {
    protected Photo photo;

    public void setPhoto(String photoUri) {
        this.setPhotoInternal(photoUri);
    }

    protected void setPhotoInternal(String photoURI) {
        if (photoURI == null) {
            this.photo = null;
        } else {
            try {
                this.photo = new Photo(Path.of(photoURI));
            } catch (InvalidPathException e) {
                this.photo = null;
            }
        }
    }

    public String getPhotoURI() {
        return (photo != null) ? photo.getPhotoFile() : null;
    }
}
