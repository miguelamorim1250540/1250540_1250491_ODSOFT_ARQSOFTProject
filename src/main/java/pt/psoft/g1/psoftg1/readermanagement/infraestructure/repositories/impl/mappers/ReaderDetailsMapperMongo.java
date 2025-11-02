package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.mappers;

import java.util.stream.Collectors;

import pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl.mappers.GenreMapperMongo;
import pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl.DataModels.ReaderDetailsDataModelMongo;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

public class ReaderDetailsMapperMongo {
    public ReaderDetails toDomain(ReaderDetailsDataModelMongo dataModel) {
        if (dataModel == null) return null;

        ReaderDetails domain = new ReaderDetails();
        //domain.setReader(dataModel.getReader());
        domain.setInterestList(dataModel.getInterestList().stream().map(genreDataModel -> GenreMapperMongo.toDomain(genreDataModel)).collect(Collectors.toList()));
        domain.setMarketingConsent(dataModel.isMarketingConsent());
        domain.setThirdPartySharingConsent(dataModel.isThirdPartySharingConsent());
        domain.setGdprConsent(dataModel.isGdprConsent());

        if (dataModel.getPhotoUri() != null) {
            domain.applyPatch(domain.getVersion() != null ? domain.getVersion() : 0,
                    new pt.psoft.g1.psoftg1.readermanagement.services.UpdateReaderRequest(), 
                    dataModel.getPhotoUri(), 
                    dataModel.getInterestList().stream().map(genreDataModel -> GenreMapperMongo.toDomain(genreDataModel)).collect(Collectors.toList()));
        }
        return domain;
    }

    public ReaderDetailsDataModelMongo toDataModel(ReaderDetails domain) {
        if (domain == null) return null;

        ReaderDetailsDataModelMongo dataModel = new ReaderDetailsDataModelMongo(domain);
        
        return dataModel;
    }
}
