package ca.jrvs.apps.stockquote.service;

import ca.jrvs.apps.stockquote.dao.Position;
import ca.jrvs.apps.util.DatabaseConnectionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class PositionServiceIntTest {

    Connection connection;
    PositionService positionService;

    @Before
    public void setUp() throws SQLException {
        connection = new DatabaseConnectionManager().getConnection();
        positionService = new PositionService(connection);
    }

    @Test
    public void buy() {
        int numShares = 10;
        double price = 10;
        Position actual = positionService.buy("AAPL", numShares, price);
        Assert.assertEquals(actual.getValuePaid(), numShares*price, 0);
    }

    @Test
    public void buyInvalidNumberOfShares() {
        int numShares = 100000000;
        double price = 10;
        Position actual = positionService.buy("AAPL", numShares, price);
        Assert.assertEquals(null, actual);
    }

    @Test
    public void sell() {
        positionService.sell("AAPL");
        Assert.assertTrue(true);
    }
}