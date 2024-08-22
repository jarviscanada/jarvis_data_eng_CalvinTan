package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.util.DatabaseConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

public class QuoteService {

    private Connection connection;
    private QuoteDAO dao;
    private QuoteHttpHelper httpHelper;

    QuoteService() {
        this.connection = new DatabaseConnectionManager("localhost", "stock_quote").getConnection();
        this.dao = new QuoteDAO(this.connection);
        this.httpHelper = new QuoteHttpHelper();
    }

    /**
     * Fetches latest quote data from endpoint
     * @param symbol
     * @return Latest quote information or empty optional if ticker symbol not found
     */
    public Optional<Quote> fetchQuoteDataFromAPI(String symbol) {
        try {
            Quote quote = this.httpHelper.fetchQuoteInfo(symbol);
            return Optional.of(quote);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
