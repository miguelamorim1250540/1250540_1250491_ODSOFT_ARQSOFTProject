package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.redis;

import java.io.Serializable;

import lombok.Getter;

public class DescriptionCache implements Serializable {
    @Getter
    String description;

    public DescriptionCache(String descritpion){
        this.description = descritpion;
    }
}
