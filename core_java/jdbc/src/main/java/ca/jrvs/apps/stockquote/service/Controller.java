package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.util.DatabaseConnectionManager;
import ca.jrvs.apps.util.LoggerUtil;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class Controller {
    static Logger logger = LoggerUtil.getLogger();
    Connection connection;
    Scanner in;

    Controller() {
        this.in = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.initClient();
    }

    /**
     * Main program
     */
    public void initClient() {
        //establish connection to database
        if (this.connectToDatabase()) {
            logger.info("Successfully connected to database");
        } else {
            return;
        }
        PositionService positionService = new PositionService(this.connection);
        QuoteService quoteService = new QuoteService(this.connection);
        this.help();

        //main program loop
        boolean quit = false;
        while(!quit) {
            String action;
            String symbol;
            int amount;
            //get input from terminal
            String[] input = in.nextLine().trim().split(" ");
            if (input.length == 0) {
                logger.warn("No input received, try again");
                continue;
            } else {
                action = input[0].toLowerCase();
            }

            switch (action) {
                case "buy":
                    if (input.length != 3) break;
                    symbol = input[1].toUpperCase();
                    try { amount = Integer.parseInt(input[2]); } catch (NumberFormatException e) {
                        logger.warn("WARNING: Amount entered too large or not an integer");
                        break;
                    }
                    Optional<Quote> quoteOptional = quoteService.fetchQuoteDataFromAPIAndInsert(symbol);
                    if (quoteOptional.isEmpty()) break;
                    Quote quote = quoteOptional.get();
                    positionService.buy(symbol, amount, quote.getPrice());
                    break;
                case "sell":
                    if (input.length != 2) break;
                    symbol = input[1].toUpperCase();
                    positionService.sell(symbol);
                    break;
                case "quote":
                    if (input.length != 2) break;
                    symbol = input[1].toUpperCase();
                    quoteService.fetchQuoteDataFromAPI(symbol, true);
                case "list":
                    if (input.length != 1) break;
                    positionService.list();
                    break;
                case "check":
                    if (input.length != 2) break;
                    symbol = input[1].toUpperCase();
                    positionService.check(symbol);
                    break;
                case "quit":
                    quit = true;
                    logger.info("Exiting program");
                case "help":
                    if (input.length != 1) break;
                    this.help();
                    break;
                default:
                    logger.warn("WARNING: Command not recognised; enter help to see a list of commands");
            }
        }
        in.close();
    }

    /**
     * establishes a connection with database
     * @return true if successfully connected
     */
    public boolean connectToDatabase() {
        //attempt to connect to database using parameters in properties file
        try {
            this.connection = new DatabaseConnectionManager().getConnection();
            return true;
        } catch (SQLException e) {
            logger.warn("WARNING: failed to connect to database using default settings, please connect manually to a database or enter quit to exit");
        }
        //asks user to manual enter database parameters
        while (this.connection == null) {
            logger.info("Enter: host database username password | quit");
            String[] input = in.nextLine().trim().split(" ");
            if (input.length == 0) {
                logger.warn("No input received, try again");
                continue;
            } else if (input.length == 1 && input[0].toLowerCase().equals("quit")) {
                logger.info("Exiting program");
                return false;
            }
            try {
                this.connection = new DatabaseConnectionManager(input[0], input[1], input[2], input[3]).getConnection();
                return true;
            } catch (SQLException e) {
                logger.error("ERROR: failed to connect to database");
            } catch (IndexOutOfBoundsException e) {
                logger.error("ERROR: not enough arguments received, try again");
            }
        }
        return false;
    }

    /**
     * Provides user with quick information on how to use client
     */
    private void help() {
        StringBuilder help = new StringBuilder();
        help.append("Commands:\n")
                .append("1. buy symbol amount : buy shares\n")
                .append("2. sell symbol : sell all shares for a position\n")
                .append("3. quote symbol : view up to date quote for symbol\n")
                .append("4. list : view all current positions\n")
                .append("5. check symbol : check current position and view gain/loss\n")
                .append("6. quit : exit program\n");
        logger.info(String.valueOf(help));
    }
}
