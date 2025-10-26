package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.mongodb;

import lombok.Getter;

public class TitleDocument {
    @Getter
    String title;

    public TitleDocument(String title){
        this.title = title;
    }
}
