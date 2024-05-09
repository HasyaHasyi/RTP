package my.uum;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Utility class for fetching data from a URL Lookup API.
 *
 * This class provides a static method to fetch information about a given URL, including the country, region, city, and ISP.
 */
public class UrlLookupAPI {
    /**
     * Declaring the API Key for the URL Lookup API.
     */
    private static final String API_KEY = "lztlYezdZEZKx1RvWt8EFp6i3J9AEk4emCy3ZblP";

    /**
     * Fetches data from the URL Lookup API for the given URL.
     *
     * @param url The URL to look up
     * @return A formatted message containing the country, region, city, and ISP information for the URL
     * @throws IOException If there's an error fetching data from the API
     */
    public static String fetchDataFromAPI(String url) throws IOException {
        // Construct the API URL with the provided URL
        URL apiUrl = new URL("https://api.api-ninjas.com/v1/urllookup?url=" + url);

        // Open the HTTP connection to the API
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

        // Set the request headers
        connection.setRequestProperty("X-Api-Key", API_KEY);
        connection.setRequestProperty("accept", "application/json");

        // Get the response code from the API
        int responseCode = connection.getResponseCode();

        // If the response code is 200 (OK), parse the JSON response and extract
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (InputStream responseStream = connection.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(responseStream);

                // Extract meaningful information from the JSON response
                String country = root.get("country").asText();
                String region = root.get("region").asText();
                String city = root.get("city").asText();
                String isp = root.get("isp").asText();

                // Create a message to send to the user
                String message = "🌍 Country: " + country + "\n🏙️ Region: " + region + "\n🏙️ City: " + city + "\n🔌 ISP: " + isp;
                return message;
            }
        } else {
            // If the response code is not 200 (Not OK), throw an IOException with the response code
            throw new IOException("API request failed with response code: " + responseCode);
        }
    }
}
