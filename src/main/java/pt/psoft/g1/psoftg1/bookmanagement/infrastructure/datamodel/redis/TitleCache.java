package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.datamodel.redis;

import java.io.Serializable;

import lombok.Getter;

public class TitleCache implements Serializable {
    @Getter
    String title;

    public TitleCache(String title) {
        this.title = title;
    }
}
