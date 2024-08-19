package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.jdbc.exercises.LoggerUtil;
import ca.jrvs.apps.jdbc.exercises.api.AlphaVantageAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class QuotteHttpHelper {

    private static final Logger logger = LoggerUtil.getLogger();
    private final OkHttpClient client;
    private final Properties properties;
    private final String apiKey;

    public QuotteHttpHelper() {
        this.properties = new Properties();
        this.loadProperties();
        this.client = new OkHttpClient();
        this.apiKey = properties.getProperty("api-key");
    }

    public static void main(String[] args) {
        QuotteHttpHelper obj = new QuotteHttpHelper();
        obj.fetchQuoteInfo("MSFT");
    }

    /**
     * fetch latest quote data from Alpha Vantage
     * @param symbol
     * @return quote data
     * @throws IllegalArgumentException no data was found for given symbol
     */
    public Quote fetchQuoteInfo(String symbol) {
        Request request = this.buildRequest(symbol);
        try {
            Response response = this.client.newCall(request).execute();
            logger.debug(response.body().string());
        } catch (IOException e) {
            logger.error("ERROR: client call failed");
        }
        return null;
    }

    private Request buildRequest(String symbol) {
        HttpUrl url = HttpUrl.parse("https://alpha-vantage.p.rapidapi.com/query").newBuilder()
                .addQueryParameter("function", "GLOBAL_QUOTE")
                .addQueryParameter("symbol", symbol)
                .addQueryParameter("datatype", "json")
                .build();
        return new Request.Builder()
                .url(url)
                .header("x-rapidapi-host", "alpha-vantage.p.rapidapi.com")
                .header("x-rapidapi-key", this.apiKey)
                .build();
    }

    private void loadProperties() {
        try {
            this.properties.load(new FileInputStream("alpha_vantage.properties"));
        } catch (IOException e) {
            logger.error("ERROR: failed to load properties");
        }
    }
}
