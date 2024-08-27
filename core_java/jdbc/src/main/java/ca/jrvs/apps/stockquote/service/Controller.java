package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.util.DatabaseConnectionManager;
import ca.jrvs.apps.util.LoggerUtil;
import org.slf4j.Logger;

import java.sql.Connection;

public class Controller {
    static Logger logger = LoggerUtil.getLogger();
    static Connection connection = new DatabaseConnectionManager("localhost", "stock_quote")
            .getConnection();
    private PositionService positionService;
    private QuoteService quoteService;
    private String action;
    private String symbol;
    private int amount;

    public Controller() {
        positionService = new PositionService(connection);
        quoteService = new QuoteService(connection);
    }

    public static void main(String[] args) {
        if (args.length < 2 || 3 < args.length) {
            logger.error("ERROR: arguments must be: buy|sell symbol [amount]");
        }
        Controller con = new Controller();
        con.setAction(args[0]);
        con.setSymbol(args[1]);
        if (con.getAction().equals("buy")) con.setAmount(args[2]);
        con.logOrder();

        switch (con.getAction()) {
            case "buy" :
                Quote quote = con.quoteService.fetchQuoteDataFromAPIAndInsert(con.getSymbol());
                con.positionService.buy(con.getSymbol(), con.getAmount() , quote.getPrice() * con.getAmount());
                break;
            case "sell" :
                con.positionService.sell(con.getSymbol());
                break;
        }
    }

    public void setAction(String action) {
        action = action.toLowerCase();
        if (!(action.equals("buy") || action.equals("sell"))) logger.error("ERROR: action must be either buy or sell");
        this.action = action;
    }

    private void logOrder() {
        StringBuilder order = new StringBuilder();
        order.append("New order received: \n")
            .append("Symbol: " + this.symbol + "\n")
            .append("Amount of shares: " + this.amount + "\n");
        logger.info(String.valueOf(order));
    }

    public String getAction() {
        return this.action;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setAmount(String amount) {
        this.amount = Integer.parseInt(amount);
    }

    public int getAmount() {
        return this.amount;
    }
}
