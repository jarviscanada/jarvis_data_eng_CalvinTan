package ca.jrvs.apps.stockquote.api;


import ca.jrvs.apps.util.LoggerUtil;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AlphaVantageAPI {

    public static Response get(OkHttpClient client, String symbol, String apiKey) {
        try {
            Request request = buildRequest(symbol, apiKey);
            Response response = client.newCall(request).execute();
            return response;
        } catch (IOException e) {
            LoggerUtil.getLogger().error("ERROR: api call failed");
        }
        return null;
    }

    private static  Request buildRequest(String symbol, String apiKey) {
        HttpUrl url = HttpUrl.parse("https://alpha-vantage.p.rapidapi.com/query").newBuilder()
                .addQueryParameter("function", "GLOBAL_QUOTE")
                .addQueryParameter("symbol", symbol)
                .addQueryParameter("datatype", "json")
                .build();
        return new Request.Builder()
                .url(url)
                .header("x-rapidapi-host", "alpha-vantage.p.rapidapi.com")
                .header("x-rapidapi-key", apiKey)
                .build();
    }
}
