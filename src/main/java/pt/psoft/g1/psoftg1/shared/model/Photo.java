package pt.psoft.g1.psoftg1.shared.model;

import java.nio.file.Path;

import lombok.Getter;

@Getter
public class Photo {
    private final String photoFile;

    public Photo(Path photoPath) {
        if (photoPath == null || photoPath.toString().isBlank()) {
            throw new IllegalArgumentException("Photo file cannot be null or blank");
        }
        this.photoFile = photoPath.toString();
    }

    @Override
    public String toString() {
        return photoFile;
    }
}
