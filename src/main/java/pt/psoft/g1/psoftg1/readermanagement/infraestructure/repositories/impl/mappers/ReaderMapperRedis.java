package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.mappers;

import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.DataModels.ReaderDataModelRedis;
import pt.psoft.g1.psoftg1.readermanagement.model.PhoneNumber;

import java.util.stream.Collectors;

@Component
public class ReaderMapperRedis {

    public ReaderDataModelRedis toDataModel(ReaderDetails domain) {
        return ReaderDataModelRedis.builder()
                .readerNumber(domain.getReaderNumber())
                .username(domain.getReader().getUsername())
                .phoneNumber(domain.getPhoneNumber())
                .birthDate(domain.getBirthDate().toString())
                .gdprConsent(domain.isGdprConsent())
                .marketingConsent(domain.isMarketingConsent())
                .thirdPartySharingConsent(domain.isThirdPartySharingConsent())
                .photoURI(domain.getPhotoURI())
                .interests(domain.getInterestList() != null
                        ? domain.getInterestList().stream().map(Object::toString).collect(Collectors.toList())
                        : null)
                .build();
    }

    public ReaderDetails toDomain(ReaderDataModelRedis dataModel) {
        if (dataModel == null) return null;

        ReaderDetails readerDetails = new ReaderDetails();
        readerDetails.setMarketingConsent(dataModel.isMarketingConsent());
        readerDetails.setThirdPartySharingConsent(dataModel.isThirdPartySharingConsent());
        readerDetails.setGdprConsent(dataModel.isGdprConsent());

        // campos que normalmente dependem do Reader, BirthDate, etc.
        // estes podem ser enriquecidos a partir da BD SQL, se necessário.

        return readerDetails;
    }
}
