package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.ID;
import ca.jrvs.apps.stockquote.dao.Quote;
import ca.jrvs.apps.stockquote.dao.QuoteDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.*;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class QuoteServiceUnitTest {

    @Mock
    Connection connection;

    QuoteService quoteService;

    @Before
    public void setUp() {
        quoteService = new QuoteService(connection);
    }

    @Test
    public void fetchQuoteDataFromAPI() {
        Optional<Quote> optional = quoteService.fetchQuoteDataFromAPI("AAPL");
        Assert.assertTrue(optional.isPresent());
        Assert.assertEquals(optional.get().getSymbol(), "AAPL");
    }
}