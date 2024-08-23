package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.util.LoggerUtil;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuoteDAO implements CrudDAO<Quote, ID> {

    private static Logger logger = LoggerUtil.getLogger();
    Connection connection;

    private static final String INSERT = "INSERT INTO quote (" +
            "symbol, open, high, low, price, " +
            "volume, latest_trading_day, previous_close," +
            "change, change_percent, timestamp) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID = "SELECT * " +
            "FROM quote " +
            "WHERE id = ?";

    private static final String SELECT_BY_SYMBOL = "SELECT * " +
            "FROM quote " +
            "WHERE symbol = ?";

    private static final String SELECT_ALL = "SELECT * " +
            "FROM quote";

    private static final String UPDATE = "UPDATE quote SET " +
            "open = ?, high = ?, low = ?, " +
            "price = ?, volume = ?, latest_trading_day = ?, " +
            "previous_close = ?, change = ?, change_percent = ?, " +
            "timestamp = ? " +
            "WHERE symbol = ?";

    private static final String DELETE = "DELETE FROM quote " +
            "WHERE id = ?";

    public QuoteDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Quote create(Quote dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(INSERT)) {
            statement.setString(1, dto.getSymbol());
            statement.setDouble(2, dto.getOpen());
            statement.setDouble(3, dto.getHigh());
            statement.setDouble(4, dto.getLow());
            statement.setDouble(5, dto.getPreviousClose());
            statement.setInt(6, dto.getVolume());
            statement.setDate(7, dto.getLatestTradingDay());
            statement.setDouble(8, dto.getPreviousClose());
            statement.setDouble(9, dto.getChange());
            statement.setString(10, dto.getChangePercent());
            statement.setTimestamp(11, dto.getTimestamp());
            statement.execute();
            return dto;
        } catch (SQLException e) {
            logger.error("ERROR: failed to insert new quote", e);
        }
        return null;
    }

    @Override
    public Quote findById(ID id) {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id.getId());
            ResultSet resultSet = statement.executeQuery();
            Quote quote = new Quote();
            while (resultSet.next()) {
                quote.setId(new ID(resultSet.getInt("id")));
                quote.setSymbol(resultSet.getString("symbol"));
                quote.setOpen(resultSet.getDouble("open"));
                quote.setHigh(resultSet.getDouble("high"));
                quote.setLow(resultSet.getDouble("low"));
                quote.setPrice(resultSet.getDouble("price"));
                quote.setVolume(resultSet.getInt("volume"));
                quote.setLatestTradingDay(resultSet.getDate("latest_trading_day"));
                quote.setPreviousClose(resultSet.getDouble("previous_close"));
                quote.setChange(resultSet.getDouble("change"));
                quote.setChangePercent(resultSet.getString("change_percent"));
                quote.setTimestamp(resultSet.getTimestamp("timestamp"));
            }
            return quote;
        } catch (SQLException e) {
            logger.error("ERROR: failed to find quote by id", e);
        }
        return null;
    }

    public Quote findBySymbol(String symbol) {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_BY_SYMBOL)) {
            statement.setString(1, symbol);
            ResultSet resultSet = statement.executeQuery();
            Quote quote = new Quote();
            while (resultSet.next()) {
                quote.setId(new ID(resultSet.getInt("id")));
                quote.setSymbol(resultSet.getString("symbol"));
                quote.setOpen(resultSet.getDouble("open"));
                quote.setHigh(resultSet.getDouble("high"));
                quote.setLow(resultSet.getDouble("low"));
                quote.setPrice(resultSet.getDouble("price"));
                quote.setVolume(resultSet.getInt("volume"));
                quote.setLatestTradingDay(resultSet.getDate("latest_trading_day"));
                quote.setPreviousClose(resultSet.getDouble("previous_close"));
                quote.setChange(resultSet.getDouble("change"));
                quote.setChangePercent(resultSet.getString("change_percent"));
                quote.setTimestamp(resultSet.getTimestamp("timestamp"));
            }
            return quote;
        } catch (SQLException e) {
            logger.error("ERROR: failed to find quote by id", e);
        }
        return null;
    }

    @Override
    public List<Quote> findAll() {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Quote> quoteList = new ArrayList<Quote>();
            while (resultSet.next()) {
                Quote tempQuote = new Quote();
                tempQuote.setId(new ID(resultSet.getInt("id")));
                tempQuote.setSymbol(resultSet.getString("symbol"));
                tempQuote.setOpen(resultSet.getDouble("open"));
                tempQuote.setHigh(resultSet.getDouble("high"));
                tempQuote.setLow(resultSet.getDouble("low"));
                tempQuote.setPrice(resultSet.getDouble("price"));
                tempQuote.setVolume(resultSet.getInt("volume"));
                tempQuote.setLatestTradingDay(resultSet.getDate("latest_trading_day"));
                tempQuote.setPreviousClose(resultSet.getDouble("previous_close"));
                tempQuote.setChange(resultSet.getDouble("change"));
                tempQuote.setTimestamp(resultSet.getTimestamp("timestamp"));
                tempQuote.setChangePercent(resultSet.getString("change_percent"));
                quoteList.add(tempQuote);
            }
            return quoteList;
        } catch (SQLException e) {
            logger.error("ERROR: failed to find quote by id", e);
        }
        return null;
    }

    @Override
    public Quote update(Quote dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE)) {
            statement.setDouble(1, dto.getOpen());
            statement.setDouble(2, dto.getHigh());
            statement.setDouble(3, dto.getLow());
            statement.setDouble(4, dto.getPreviousClose());
            statement.setInt(5, dto.getVolume());
            statement.setDate(6, dto.getLatestTradingDay());
            statement.setDouble(7, dto.getPreviousClose());
            statement.setDouble(8, dto.getChange());
            statement.setString(9, dto.getChangePercent());
            statement.setTimestamp(10, dto.getTimestamp());
            statement.setString(11, dto.getSymbol());
            statement.execute();
            return dto;
        } catch (SQLException e) {
            logger.error("ERROR: failed to insert new quote", e);
        }
        return null;
    }

    @Override
    public void delete(ID id) {
        try (PreparedStatement statement = this.connection.prepareStatement(DELETE)) {
            statement.setLong(1, id.getId());
            statement.execute();
        } catch (SQLException e) {
            logger.error("ERROR: failed to delete by id", e);
        }
    }
}
