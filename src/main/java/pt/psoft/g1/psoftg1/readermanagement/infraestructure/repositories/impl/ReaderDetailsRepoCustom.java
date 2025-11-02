package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl;

import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.services.SearchReadersQuery;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.util.List;

public interface ReaderDetailsRepoCustom {
    List<ReaderDetails> searchReaderDetails(Page page, SearchReadersQuery query);
}
