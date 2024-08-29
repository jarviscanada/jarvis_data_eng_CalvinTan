package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import ca.jrvs.apps.stockquote.dao.QuoteHttpHelper;
import ca.jrvs.apps.stockquote.dao.SymbolNotFoundException;
import ca.jrvs.apps.util.LoggerUtil;
import org.slf4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

public class QuoteService {

    private static Logger logger = LoggerUtil.getLogger();
    private QuoteDAO dao;
    private QuoteHttpHelper httpHelper;

    QuoteService(Connection connection) {
        this.dao = new QuoteDAO(connection);
        this.httpHelper = new QuoteHttpHelper();
    }

    public Quote findBySymbol(String symbol) {
        return dao.findBySymbol(symbol);
    }

    /**
     * Fetches latest quote data from endpoint
     * @param symbol
     * @return Latest quote information or empty optional if ticker symbol not found
     */
    public Optional<Quote> fetchQuoteDataFromAPI(String symbol, boolean log) {
        try {
            Quote quote = this.httpHelper.fetchQuoteInfo(symbol);
            if (log) this.logQuote(quote);
            return Optional.of(quote);
        } catch (IOException | SymbolNotFoundException e) {
            logger.error("ERROR: symbol not found");
            return Optional.empty();
        }
    }

    public Optional<Quote> fetchQuoteDataFromAPI(String symbol) {
        return this.fetchQuoteDataFromAPI(symbol, false);
    }

    public Optional<Quote> fetchQuoteDataFromAPIAndInsert(String symbol) {
        Optional<Quote> quoteOptional = fetchQuoteDataFromAPI(symbol);
        if (quoteOptional.isEmpty()) {
            logger.error("ERROR: symbol not found");
            return quoteOptional;
        }
        Quote quote = quoteOptional.get();
        Quote prevQuote = dao.findBySymbol(symbol);
        if (prevQuote == null) {
            dao.create(quote);
        } else {
            dao.update(quote);
        }
        return quoteOptional;
    }

    private void logQuote(Quote quote) {
        StringBuilder quoteString = new StringBuilder();
        quoteString.append("Symbol: " + quote.getSymbol() + "\n")
                .append("Current Price: " + quote.getPrice() + "\n")
                .append("Time retrieved: " + quote.getTimestamp() + "\n");
        logger.info(String.valueOf(quoteString));
    }


}
