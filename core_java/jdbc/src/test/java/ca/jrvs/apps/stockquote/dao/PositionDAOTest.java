package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.util.DatabaseConnectionManager;
import ca.jrvs.apps.util.LoggerUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class PositionDAOTest {

    @Mock
    Connection connection;
    @Mock
    PreparedStatement statement;
    @Mock
    ResultSet resultSet;

    PositionDAO positionDAO;
    static Position position;

    @BeforeClass
    public static void setUpQuote() {
        position = new Position();
        position.setId(new ID(1));
        position.setSymbol("TEST");
        position.setNumOfShares(10);
        position.setValuePaid(100);
    }

    @Before
    public void setUp() throws Exception {
        Mockito.when(connection.prepareStatement(any(String.class))).thenReturn(statement);
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true, false);
        Mockito.when(resultSet.getString("symbol")).thenReturn(position.getSymbol());
        Mockito.when(resultSet.getInt("number_of_shares")).thenReturn(position.getNumOfShares());
        Mockito.when(resultSet.getDouble("value_paid")).thenReturn(position.getValuePaid());

        positionDAO = new PositionDAO(connection);
    }

    @Test
    public void create() {
        Position tempPosition = positionDAO.create(position);
        Assert.assertEquals(position.getSymbol(), tempPosition.getSymbol());
        Assert.assertEquals(position.getNumOfShares(), tempPosition.getNumOfShares());
        Assert.assertEquals(position.getValuePaid(), tempPosition.getValuePaid(), 0);
    }

    @Test
    public void findBySymbol() {
        Assert.assertTrue(position != null);
        Position tempPosition = positionDAO.findBySymbol("TEST");
        Assert.assertEquals(position.getSymbol(), tempPosition.getSymbol());
        Assert.assertEquals(position.getNumOfShares(), tempPosition.getNumOfShares());
        Assert.assertEquals(position.getValuePaid(), tempPosition.getValuePaid(), 0);
    }

    @Test
    public void update() {
        Position tempPosition = positionDAO.update(position);
        Assert.assertEquals(position.getSymbol(), tempPosition.getSymbol());
        Assert.assertEquals(position.getNumOfShares(), tempPosition.getNumOfShares());
        Assert.assertEquals(position.getValuePaid(), tempPosition.getValuePaid(), 0);
    }
}