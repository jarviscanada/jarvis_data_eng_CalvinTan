package ca.jrvs.apps.jdbc.exercises.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class AlphaVantageAPI {

    private Properties properties;
    private String symbol;
    private String apiKey;

    public AlphaVantageAPI(String symbol) throws IOException {
        this.properties = new Properties();
        this.loadProperties();
        this.symbol = symbol;
        this.apiKey = properties.getProperty("api-key");
    }

    public static void main(String[] args) {
        try {
            AlphaVantageAPI test = new AlphaVantageAPI("MSFT");
            HttpResponse response = test.get();
            System.out.println(response.body());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public HttpResponse get() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&datatype=json"))
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (InterruptedException | JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadProperties() throws IOException {
        this.properties.load(new FileInputStream("alpha_vantage.properties"));
    }
}
