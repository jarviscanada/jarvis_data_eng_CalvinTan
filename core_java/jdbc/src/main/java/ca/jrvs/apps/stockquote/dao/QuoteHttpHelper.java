package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.stockquote.api.AlphaVantageAPI;
import ca.jrvs.apps.stockquote.json.JsonParser;
import ca.jrvs.apps.util.LoggerUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.slf4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Properties;

public class QuoteHttpHelper {

    private static final Logger logger = LoggerUtil.getLogger();
    private final OkHttpClient client;
    private final Properties properties;
    private final String apiKey;

    public QuoteHttpHelper() {
        this.properties = new Properties();
        this.loadProperties();
        this.client = new OkHttpClient();
        this.apiKey = properties.getProperty("api-key");
    }

    public static void main(String[] args) {
        QuoteHttpHelper obj = new QuoteHttpHelper();
        try {
            Quote quote = obj.fetchQuoteInfo("MSFT");
            logger.debug(quote.getSymbol() + ": " + quote.getPrice());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * fetch latest quote data from Alpha Vantage
     * @param symbol
     * @return quote data
     * @throws IllegalArgumentException no data was found for given symbol
     */
    public Quote fetchQuoteInfo(String symbol) throws IOException {
        Response response = AlphaVantageAPI.get(this.client, symbol, this.apiKey);
        Quote quote = JsonParser.toObjectFromJson(response.body().string(), Quote.class);
        quote.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        return quote;
    }

    private void loadProperties() {
        try {
            this.properties.load(new FileInputStream("alpha_vantage.properties"));
        } catch (IOException e) {
            logger.error("ERROR: failed to load or use api key");
        }
    }
}
