package pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl.DataModels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.model.Photo;

import java.nio.file.Path;

@Entity
@Getter
@Setter
@Table(name = "photos")
public class PhotoDataModelSQL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String photoFile;

    public static PhotoDataModelSQL fromDomain(Photo photo) {
        PhotoDataModelSQL model = new PhotoDataModelSQL();
        model.setPhotoFile(photo.getPhotoFile());
        return model;
    }

    public Photo toDomain() {
        return new Photo(Path.of(photoFile)); // ✅ converte String → Path
    }
}
