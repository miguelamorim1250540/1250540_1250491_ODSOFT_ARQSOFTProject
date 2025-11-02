package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.DataModels;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.DataModels.GenreDataModelMongo;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.mappers.GenreMapperMongo;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;


@Document(collection = "readers")
public class ReaderDetailsDataModelMongo {
    @Id
    private String id;

    @Getter
    private String readerNumber;

    @Getter
    private LocalDate birthDate;

    @Getter
    private String phoneNumber;

    @Getter
    private boolean gdprConsent;
    @Getter
    private boolean marketingConsent;
    @Getter
    private boolean thirdPartySharingConsent;

    @Getter
    private Long version;

    @Getter
    private List<GenreDataModelMongo> interestList;

    @Getter
    private String photoUri;

    public ReaderDetailsDataModelMongo(ReaderDetails reader){    
        this.readerNumber = reader.getReaderNumber();
        this.birthDate = reader.getBirthDate().getBirthDate();
        this.phoneNumber = reader.getPhoneNumber();
        this.gdprConsent = reader.isGdprConsent();
        this.marketingConsent = reader.isMarketingConsent();
        this.thirdPartySharingConsent = reader.isThirdPartySharingConsent();
        this.version = reader.getVersion();
        this.photoUri = reader.getPhotoURI();
        this.interestList = reader.getInterestList().stream().map(
            genre -> GenreMapperMongo.toDataModel(genre)
        ).collect(Collectors.toList());
    }
}
