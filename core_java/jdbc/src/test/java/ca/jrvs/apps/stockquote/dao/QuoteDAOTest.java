package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.util.DatabaseConnectionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class QuoteDAOTest {

    @Mock
    Connection connection;
    @Mock
    PreparedStatement statement;
    @Mock
    ResultSet resultSet;

    QuoteDAO quoteDAO;
    static Quote testQuote;

    @BeforeClass
    public static void setUpQuote() {
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
    }

    @Before
    public void setUp() throws Exception {
        Mockito.when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true, false);
        Mockito.when(resultSet.getInt("id")).thenReturn(testQuote.getId().toInt());
        Mockito.when(resultSet.getString("symbol")).thenReturn(testQuote.getSymbol());
        Mockito.when(resultSet.getDouble("open")).thenReturn(testQuote.getOpen());
        Mockito.when(resultSet.getDouble("high")).thenReturn(testQuote.getHigh());
        Mockito.when(resultSet.getDouble("low")).thenReturn(testQuote.getLow());
        Mockito.when(resultSet.getDouble("price")).thenReturn((testQuote.getPrice()));
        Mockito.when(resultSet.getInt("volume")).thenReturn(testQuote.getVolume());
        Mockito.when(resultSet.getDate("latest_trading_day")).thenReturn(testQuote.getLatestTradingDay());
        Mockito.when(resultSet.getDouble("previous_close")).thenReturn(testQuote.getPreviousClose());
        Mockito.when(resultSet.getDouble("change")).thenReturn(testQuote.getChange());
        Mockito.when(resultSet.getString("change_percent")).thenReturn(testQuote.getChangePercent());
        Mockito.when(resultSet.getTimestamp("timestamp")).thenReturn(testQuote.getTimestamp());

        quoteDAO = new QuoteDAO(connection);
    }

    @Test
    public void create() {
        Quote tempQuote = quoteDAO.create(testQuote);
        Assert.assertEquals(testQuote.getSymbol(), tempQuote.getSymbol());
        Assert.assertEquals(testQuote.getHigh(), tempQuote.getHigh(), 0);
        Assert.assertEquals(testQuote.getLow(), tempQuote.getLow(), 0);
    }

    @Test
    public void findBySymbol() {
        Assert.assertTrue(testQuote != null);
        Quote tempQuote = quoteDAO.findBySymbol("TEST");
        Assert.assertEquals(testQuote.getSymbol(), tempQuote.getSymbol());
        Assert.assertEquals(testQuote.getHigh(), tempQuote.getHigh(), 0);
        Assert.assertEquals(testQuote.getLow(), tempQuote.getLow(), 0);
    }

    @Test
    public void update() {
        Quote tempQuote = quoteDAO.update(testQuote);
        Assert.assertEquals(testQuote.getSymbol(), tempQuote.getSymbol());
        Assert.assertEquals(testQuote.getHigh(), tempQuote.getHigh(), 0);
        Assert.assertEquals(testQuote.getLow(), tempQuote.getLow(), 0);
    }
}