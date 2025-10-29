package pt.psoft.g1.psoftg1.external.openlibraryapi.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class OpenLibraryAPIResponse {
    @Getter
    @Setter
    private int numFound;
    
    @Getter
    @Setter
    private List<Doc> docs;

    @Getter
    @Setter
    public static class Doc {
        private String title;
        private List<String> author_name;  
    }
}
