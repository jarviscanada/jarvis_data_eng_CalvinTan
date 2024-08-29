package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.util.DatabaseConnectionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class QuoteServiceIntTest {

    Connection connection;
    QuoteService quoteService;

    @Before
    public void setUp() throws SQLException {
        connection = new DatabaseConnectionManager().getConnection();
        quoteService = new QuoteService(connection);
    }

    @Test
    public void fetchQuoteDataFromAPI() {
        Optional<Quote> optional = quoteService.fetchQuoteDataFromAPI("AAPL");
        Assert.assertTrue(optional.isPresent());
        Assert.assertEquals(optional.get().getSymbol(), "AAPL");
    }
}