package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.ID;
import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.stockquote.dao.PositionDAO;
import ca.jrvs.apps.stockquote.dao.Quote;
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
public class PositionServiceUnitTest {

    @Mock
    Connection connection;
    @Mock
    PreparedStatement statement;
    @Mock
    ResultSet resultSet;
    @Mock
    QuoteService quoteService;

    PositionService positionService;
    Quote testQuote;
    PositionService testPosition;

    public void setUpMockito() throws SQLException {
        Mockito.when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true,false);
        Mockito.when(quoteService.findBySymbol(any(String.class))).thenReturn(testQuote);
        Mockito.when(quoteService.fetchQuoteDataFromAPI(any(String.class))).thenReturn(Optional.of(testQuote));
    }

    @Before
    public void setUp() throws SQLException {
        positionService = new PositionService(connection);
        positionService.setQuoteService(quoteService);
        testQuote = new Quote();
        testQuote.setId(new ID(1));
        testQuote.setSymbol("TEST");
        testQuote.setOpen(11);
        testQuote.setHigh(22);
        testQuote.setLow(33);
        testQuote.setPrice(44);
        testQuote.setVolume(555);
        testQuote.setLatestTradingDay(new Date(new java.util.Date().getTime()));
        testQuote.setPreviousClose(66);
        testQuote.setChange(77);
        testQuote.setChangePercent("88%");
        testQuote.setTimestamp(null);
        setUpMockito();
    }

    @Test
    public void buy() {
        int numShares = 10;
        double price = 10;
        Position actual = positionService.buy("TEST", numShares, price);
        Assert.assertEquals(actual.getValuePaid(), numShares*price, 0);
    }

    @Test
    public void buyInvalidNumberOfShares() {
        int numShares = 100000000;
        double price = 10;
        Position actual = positionService.buy("TEST", numShares, price);
        Assert.assertEquals(null, actual);
    }

    @Test
    public void sell() {
        positionService.sell("TEST");
        Assert.assertTrue(true);
    }
}