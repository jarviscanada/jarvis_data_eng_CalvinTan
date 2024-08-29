package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.PositionDAO;
import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.util.LoggerUtil;
import org.slf4j.Logger;

import java.sql.Connection;
import java.util.Optional;

public class PositionService {

    private static Logger logger = LoggerUtil.getLogger();
    private PositionDAO dao;
    private QuoteService quoteService;

    PositionService(Connection connection) {
        dao = new PositionDAO(connection);
        quoteService = new QuoteService(connection);
    }

    /**
     * Processes a buy order and updates the database accordingly
     * @param symbol
     * @param numberOfShares
     * @param price
     * @return The position in our database after processing the buy
     */
    public Position buy(String symbol, int numberOfShares, double price) {
        double totalPaid = numberOfShares*price;
        Position newPosition;
        Position prevPosition;
        Quote quote = quoteService.findBySymbol(symbol);
        if (quote.getVolume() <= numberOfShares) {
            logger.error("ERROR: purchasing more shares than available. Try again with smaller order");
            return null;
        }

        prevPosition = dao.findBySymbol(symbol);
        if (prevPosition == null) {
            newPosition = newPosition(symbol, numberOfShares, totalPaid);
            dao.create(newPosition);
        } else {
            newPosition = newPosition(symbol,
                    numberOfShares + prevPosition.getNumOfShares(),
                    totalPaid + prevPosition.getValuePaid());
            dao.update(newPosition);
        }
        logBuy(newPosition);
        return newPosition;
    }

    /**
     * Sells all shares of the given ticker symbol
     * @param symbol
     */
    public void sell(String symbol) {
        Optional<Quote> quoteOptional = quoteService.fetchQuoteDataFromAPI(symbol);
        Position position = dao.findBySymbol(symbol);
        if (quoteOptional.isEmpty()) {
            logger.error("ERROR: up to date info on symbol not found");
        } else if (position == null) {
            logger.error("ERROR: no position found for this symbol");
        } else {
            Quote quote = quoteOptional.get();
            dao.delete(symbol);
            logSell(quote, position);
        }
    }

    private void logBuy(Position position) {
        StringBuilder buyLog = new StringBuilder();
        buyLog.append(position.getSymbol() + " Bought successfully\n")
                .append("total shares: " + position.getNumOfShares() + "\n")
                .append("total amount paid: " + position.getValuePaid() + "\n");
        logger.info(String.valueOf(buyLog));
    }

    private void logSell(Quote quote, Position position) {
        StringBuilder sellLog = new StringBuilder();
        sellLog.append(position.getSymbol() + " Sold successfully\n")
                .append("sell price: " + quote.getPrice() + "\n")
                .append("profit/loss: " + (quote.getPrice()*position.getNumOfShares() - position.getValuePaid()) + "\n");
        logger.info(String.valueOf(sellLog));
    }

    private Position newPosition(String symbol, int numberOfShares, double price) {
        Position newPosition = new Position();
        newPosition.setSymbol(symbol);
        newPosition.setNumOfShares(numberOfShares);
        newPosition.setValuePaid(price);
        return newPosition;
    }

    public void setQuoteService(QuoteService quoteService) {
        this.quoteService = quoteService;
    }
}