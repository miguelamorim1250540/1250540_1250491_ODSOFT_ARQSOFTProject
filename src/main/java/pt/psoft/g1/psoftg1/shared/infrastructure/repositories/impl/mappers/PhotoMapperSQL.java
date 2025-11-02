package pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl.DataModels.PhotoDataModelSQL;
import pt.psoft.g1.psoftg1.shared.model.Photo;

import java.nio.file.Path;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhotoMapperSQL {

    // ✅ Usa o construtor de Photo, não um setter
    default Photo toDomain(PhotoDataModelSQL model) {
        if (model == null || model.getPhotoFile() == null) return null;
        return new Photo(Path.of(model.getPhotoFile()));
    }

    default PhotoDataModelSQL toDataModel(Photo domain) {
        if (domain == null) return null;
        PhotoDataModelSQL dataModel = new PhotoDataModelSQL();
        dataModel.setPhotoFile(domain.getPhotoFile());
        return dataModel;
    }
}
