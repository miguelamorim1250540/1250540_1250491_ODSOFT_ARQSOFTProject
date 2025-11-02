package pt.psoft.g1.psoftg1.bookmanagement.api.isbnmanagement.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import pt.psoft.g1.psoftg1.bookmanagement.api.isbnmanagement.infastructure.IsbnProvider;


@Component
public class GoogleBooksProvider implements IsbnProvider {

    private static final String GOOGLE_BOOKS_API = "https://www.googleapis.com/books/v1/volumes?q=intitle:";

    @Override
    public String getIsbnByTitle(String title) {
        try {
            String encodedTitle = UriUtils.encode(title, "UTF-8");
            String url = GOOGLE_BOOKS_API + encodedTitle;

            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            if (response == null) return null;

            JSONObject json = new JSONObject(response);
            JSONArray items = json.optJSONArray("items");
            if (items == null || items.isEmpty()) return null;

            // Get first result
            JSONObject volumeInfo = items.getJSONObject(0).getJSONObject("volumeInfo");
            JSONArray identifiers = volumeInfo.optJSONArray("industryIdentifiers");
            if (identifiers == null || identifiers.isEmpty()) return null;

            return identifiers.getJSONObject(0).optString("identifier", null);

        } catch (Exception e) {
            return null;
        }
    }
}
