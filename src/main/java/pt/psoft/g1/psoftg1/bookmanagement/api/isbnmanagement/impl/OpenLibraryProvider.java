package pt.psoft.g1.psoftg1.bookmanagement.api.isbnmanagement.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import pt.psoft.g1.psoftg1.bookmanagement.api.isbnmanagement.infastructure.IsbnProvider;


@Component
public class OpenLibraryProvider implements IsbnProvider {

    private static final String OPEN_LIBRARY_API = "https://openlibrary.org/search.json?title=";

    @Override
    public String getIsbnByTitle(String title) {
        try {
            String encodedTitle = UriUtils.encode(title, "UTF-8");
            String url = OPEN_LIBRARY_API + encodedTitle;

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            if (response == null) return null;

            JSONObject json = new JSONObject(response);
            JSONArray docs = json.optJSONArray("docs");
            if (docs == null || docs.isEmpty()) return null;

            JSONArray isbnArray = docs.getJSONObject(0).optJSONArray("isbn");
            if (isbnArray == null || isbnArray.isEmpty()) return null;

            return isbnArray.getString(0);

        } catch (Exception e) {
            return null;
        }
    }
}

