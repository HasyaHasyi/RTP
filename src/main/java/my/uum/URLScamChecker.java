package my.uum;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Utility class for checking the safety status of a given URL by querying an external API.
 */
public class URLScamChecker {

    /**
     * Checks the safety status of a URL by querying an external API.
     *
     * @param url The URL to check for safety status
     * @return A formatted message containing information about the safety status of the URL
     */
    public static String checkURL(String url) {
        try {
            String apiUrl = "https://www.ipqualityscore.com/api/json/url/2k9A8jOphwralR0WIMOHuJfgV57h1zmZ/" + url;

            URL urlConnection = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStream responseStream = connection.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(responseStream);

                    // Extract meaningful information from the JSON response
                    String domain = root.get("domain").asText();
                    boolean malware = root.get("malware").asBoolean();
                    boolean phishing = root.get("phishing").asBoolean();
                    boolean suspicious = root.get("suspicious").asBoolean();
                    String ipAddress = root.get("ip_address").asText();
                    boolean spamming = root.get("spamming").asBoolean();
                    boolean adult = root.get("adult").asBoolean();
                    boolean redirected = root.get("redirected").asBoolean();

                    // Create a message to send to the user
                    StringBuilder sb = new StringBuilder();
                    sb.append("\uD83C\uDF10 Domain: ").append(domain).append("\n");
                    sb.append("🚨 Malware: ").append(malware).append("\n");
                    sb.append("🔒 Phishing: ").append(phishing).append("\n");
                    sb.append("🔍 Suspicious: ").append(suspicious).append("\n");
                    sb.append("\uD83C\uDF10 IP Address: ").append(ipAddress).append("\n");
                    sb.append("🚫 Spamming: ").append(spamming).append("\n");
                    sb.append("🔞 Adult: ").append(adult).append("\n");
                    sb.append("🔄 Redirected: ").append(redirected).append("\n");

                    return sb.toString();
                }
            } else {
                return "Scam Checker Info:\nError: " + responseCode + " - " + connection.getResponseMessage();
            }
        } catch (IOException e) {
            return "Scam Checker Info:\nError: " + e.getMessage();
        }
    }
}
