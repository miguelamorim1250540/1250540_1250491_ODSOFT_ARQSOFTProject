package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.mappers;

import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderNumber;
import pt.psoft.g1.psoftg1.readermanagement.model.PhoneNumber;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.DataModels.ReaderDetailsDataModelSQL;
import pt.psoft.g1.psoftg1.readermanagement.model.BirthDate;

@Component
public class ReaderDetailsMapper {

    public ReaderDetails toDomain(ReaderDetailsDataModelSQL dataModel) {
        if (dataModel == null) return null;

        ReaderDetails domain = new ReaderDetails();
        domain.setReader(dataModel.getReader());
        domain.setInterestList(dataModel.getInterestList());
        domain.setMarketingConsent(dataModel.isMarketingConsent());
        domain.setThirdPartySharingConsent(dataModel.isThirdPartySharingConsent());
        domain.setGdprConsent(dataModel.isGdprConsent());

        // usar o método público de ReaderDetails, não o protected setPhotoInternal
        if (dataModel.getPhotoUri() != null) {
            domain.applyPatch(domain.getVersion() != null ? domain.getVersion() : 0,
                    new pt.psoft.g1.psoftg1.readermanagement.services.UpdateReaderRequest(), 
                    dataModel.getPhotoUri(), 
                    dataModel.getInterestList());
        }
        return domain;
    }

    public ReaderDetailsDataModelSQL toDataModel(ReaderDetails domain) {
        if (domain == null) return null;

        ReaderDetailsDataModelSQL dataModel = new ReaderDetailsDataModelSQL();
        dataModel.setReader(domain.getReader());

        if (domain.getReaderNumber() != null) {
            // parse manual do formato "YYYY/NNN"
            String[] parts = domain.getReaderNumber().split("/");
            try {
                int year = Integer.parseInt(parts[0]);
                int number = Integer.parseInt(parts[1]);
                dataModel.setReaderNumber(new ReaderNumber(year, number));
            } catch (Exception e) {
                // fallback genérico
                dataModel.setReaderNumber(new ReaderNumber(0, 0));
            }
        }

        dataModel.setBirthDate(domain.getBirthDate());
        dataModel.setPhoneNumber(new PhoneNumber(domain.getPhoneNumber()));
        dataModel.setGdprConsent(domain.isGdprConsent());
        dataModel.setMarketingConsent(domain.isMarketingConsent());
        dataModel.setThirdPartySharingConsent(domain.isThirdPartySharingConsent());
        dataModel.setInterestList(domain.getInterestList());
        dataModel.setPhotoUri(domain.getPhotoURI());
        return dataModel;
    }
}
