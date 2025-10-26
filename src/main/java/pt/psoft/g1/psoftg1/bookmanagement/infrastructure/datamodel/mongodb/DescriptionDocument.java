package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.mongodb;

import lombok.Getter;

public class DescriptionDocument {
    @Getter
    String description;

    public DescriptionDocument(String description){
        this.description = description;
    }
}
