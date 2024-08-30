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
    PositionService positionService;
    QuoteService quoteService;
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
            System.out.println("Successfully connected to database");
        } else {
            return;
        }
        System.out.println("Welcome to Stock Quote App");
        positionService = new PositionService(this.connection);
        quoteService = new QuoteService(this.connection);
        this.help();

        //main program loop
        boolean quit = false;
        while(!quit) {
            String action;
            System.out.print("> ");
            String[] input = in.nextLine().trim().split(" ");
            if (input.length == 0) {
                System.out.println("No input received, try again");
                continue;
            } else {
                action = input[0].toLowerCase();
            }
            switch (action) {
                case "buy":
                    buy(input);
                    break;
                case "sell":
                    sell(input);
                    break;
                case "quote":
                    quote(input);
                    break;
                case "list":
                    list(input);
                    break;
                case "check":
                    check(input);
                    break;
                case "quit":
                    quit = true;
                    System.out.println("Exiting program");
                    break;
                case "help":
                    if (input.length != 1) break;
                    this.help();
                    break;
                default:
                    System.out.println("WARNING: Command not recognised; enter help to see a list of commands");
            }
        }
        in.close();
    }

    public void buy(String[] input) {
        if (!checkNumArgs(input, 3)) return;
        int amount;
        String symbol = input[1].toUpperCase();
        try { amount = Integer.parseInt(input[2]); } catch (NumberFormatException e) {
            System.out.println("WARNING: Amount entered too large or not an integer");
            return;
        }
        Optional<Quote> quoteOptional = quoteService.fetchQuoteDataFromAPIAndInsert(symbol);
        if (quoteOptional.isEmpty()) return;
        Quote quote = quoteOptional.get();
        positionService.buy(symbol, amount, quote.getPrice());
    }

    public void sell(String[] input) {
        if (!checkNumArgs(input, 2)) return;
        String symbol = input[1].toUpperCase();
        positionService.sell(symbol);
    }

    public void quote(String[] input) {
        if (!checkNumArgs(input, 2)) return;
        String symbol = input[1].toUpperCase();
        quoteService.fetchQuoteDataFromAPI(symbol, true);
    }

    public void list(String[] input) {
        if (!checkNumArgs(input, 1)) return;
        positionService.list();
    }

    public void check(String[] input) {
        if (!checkNumArgs(input, 2)) return;
        String symbol = input[1].toUpperCase();
        positionService.check(symbol);
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
            System.out.println("WARNING: failed to connect to database using default settings, please connect manually to a database or enter quit to exit");
        }
        //asks user to manual enter database parameters
        while (this.connection == null) {
            System.out.println("Enter: host database username password | quit");
            System.out.print("> ");
            String[] input = in.nextLine().trim().split(" ");
            if (input.length == 0) {
                System.out.println("No input received, try again");
                continue;
            } else if (input.length == 1 && input[0].toLowerCase().equals("quit")) {
                System.out.println("Exiting program");
                return false;
            }
            try {
                this.connection = new DatabaseConnectionManager(input[0], input[1], input[2], input[3]).getConnection();
                return true;
            } catch (SQLException e) {
                System.out.println("ERROR: failed to connect to database");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("ERROR: not enough arguments received, try again");
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
        System.out.println(help);
    }

    private boolean checkNumArgs(String[] input, int expected) {
        if (input.length == expected) {
            return true;
        }
        System.out.println("WARNING: Command not recognised; enter help to see a list of commands");
        return false;
    }
}
