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
     * @return The position in our database after processing the buy
     */
    public Position buy(String symbol, int numberOfShares, double price) {
        Position newPosition;
        Position prevPosition;
        Quote quote = quoteService.findBySymbol(symbol);
        if (quote.getVolume() <= numberOfShares) {
            logger.error("ERROR: purchasing more shares than available. Try again with smaller order");
        }

        prevPosition = dao.findBySymbol(symbol);
        if (prevPosition == null) {
            newPosition = newPosition(symbol, numberOfShares, price);
            dao.create(newPosition);
        } else {
            newPosition = newPosition(symbol,
                    numberOfShares + prevPosition.getNumOfShares(),
                    price + prevPosition.getValuePaid());
            dao.update(newPosition);
        }
        return newPosition;
    }

    /**
     * Sells all shares of the given ticker symbol
     * @param symbol
     */
    public void sell(String symbol) {
        dao.delete(symbol);
    }

    private Position newPosition(String symbol, int numberOfShares, double price) {
        Position newPosition = new Position();
        newPosition.setSymbol(symbol);
        newPosition.setNumOfShares(numberOfShares);
        newPosition.setValuePaid(price);
        return newPosition;
    }
}