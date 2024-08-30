package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.PositionDAO;
import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.util.LoggerUtil;
import org.slf4j.Logger;

import java.sql.Connection;
import java.util.List;
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
        if (numberOfShares < 0) {
            logger.error("ERROR: can not purchase negative number of shares");
            return null;
        }
        double totalPaid = numberOfShares*price;
        Position newPosition;
        Position prevPosition;
        Quote quote = quoteService.findBySymbol(symbol);
        if (quote.getVolume() <= numberOfShares) {
            logger.error("ERROR: purchasing more shares than available. Try again with smaller order");
            return null;
        }

        prevPosition = dao.findBySymbol(symbol);
        //check if position is already held for the given symbol
        if (prevPosition == null) {
            newPosition = newPosition(symbol, numberOfShares, totalPaid);
            dao.create(newPosition);
        //update old position if position already exists for symbol
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
        if (quoteOptional.isEmpty()) return;
        Position position = dao.findBySymbol(symbol);
        if (position == null) {
            logger.error("ERROR: no position found for this symbol");
        } else {
            Quote quote = quoteOptional.get();
            dao.delete(symbol);
            logSell(quote, position);
        }
    }

    /**
     * List all positions held
     */
    public void list() {
        List<Position> positions = dao.findAll();
        logPositions(positions);
    }

    /**
     * Checks position and computes gain/loss from current quote
     * @param symbol
     */
    public void check(String symbol) {
        Optional<Quote> quoteOptional = quoteService.fetchQuoteDataFromAPI(symbol);
        if (quoteOptional.isEmpty()) return;
        Position position = dao.findBySymbol(symbol);
        if (position == null) {
            logger.error("ERROR: no position found for this symbol");
        } else {
            Quote quote = quoteOptional.get();
            logCheck(quote, position);
        }
    }

    /**
     * logs successful buy
     * @param position
     */
    private void logBuy(Position position) {
        StringBuilder buyLog = new StringBuilder();
        buyLog.append(position.getSymbol() + " Bought successfully\n")
                .append("total shares: " + position.getNumOfShares() + "\n")
                .append("total amount paid: " + position.getValuePaid() + "\n");
        logger.info(String.valueOf(buyLog));
    }

    /**
     * logs successful sell
     * @param quote
     * @param position
     */
    private void logSell(Quote quote, Position position) {
        double netChange = Math.round((quote.getPrice()*position.getNumOfShares() - position.getValuePaid())*100) / 100.0;
        StringBuilder sellLog = new StringBuilder();
        sellLog.append(position.getSymbol() + " Sold successfully\n")
                .append("sell price: " + quote.getPrice() + "\n")
                .append("profit/loss: " + netChange + "\n");
        logger.info(String.valueOf(sellLog));
    }

    /**
     * logs all currently held positions
     * @param positions
     */
    private void logPositions(List<Position> positions) {
        StringBuilder output = new StringBuilder();
        output.append("Current Positions:\n");
        for (Position p : positions) {
            output.append("Symbol: " + p.getSymbol() + "\n")
                    .append("Number of shares: " + p.getNumOfShares() + "\n")
                    .append("Amount Paid: " + p.getValuePaid() + "\n");
        }
        logger.info(String.valueOf(output));
    }

    /**
     * compares and logs position and quote
     * @param quote
     * @param position
     */
    private void logCheck(Quote quote, Position position) {
        double pricePerShare = Math.round((position.getValuePaid()/position.getNumOfShares())*100) / 100.0;
        double percentChange = Math.round((quote.getPrice()/pricePerShare)*100) / 100.0;
        double netChange = Math.round((quote.getPrice()*position.getNumOfShares() - position.getValuePaid())*100) / 100.0;
        StringBuilder log = new StringBuilder();
        log.append("Symbol: " + position.getSymbol() + "\n")
                .append("Number of shares: " + position.getNumOfShares() + "\n")
                .append("Amount Paid: " + position.getValuePaid() + "\n")
                .append("Price per share: " + pricePerShare + "\n") //round to 2 decimal places
                .append("Current price per share: " + quote.getPrice() + "\n")
                .append("Net change: " + netChange + " (" + percentChange +"%)\n");
        logger.info(String.valueOf(log));
    }

    /**
     * Helper method to create a new position object
     */
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