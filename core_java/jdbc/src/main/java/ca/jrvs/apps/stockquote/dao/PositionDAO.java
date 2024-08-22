package ca.jrvs.apps.stockquote.dao;

import ca.jrvs.apps.util.LoggerUtil;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionDAO implements CrudDAO<Position, ID> {

    private static Logger logger = LoggerUtil.getLogger();
    private Connection connection;

    private static final String INSERT = "INSERT INTO position (" +
            "symbol, number_of_shares, value_paid) " +
            "VALUES (?, ?, ?)";

    private static final String SELECT_BY_ID = "SELECT * " +
            "FROM position " +
            "WHERE id = ?";

    private static final String SELECT_ALL = "SELECT * " +
            "FROM position";

    private static final String DELETE = "DELETE FROM position " +
            "WHERE id = ?";

    public PositionDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Position create(Position dto) {
        try (PreparedStatement statement = this.connection.prepareStatement(INSERT)) {
            statement.setString(1, dto.getSymbol());
            statement.setInt(2, dto.getNumOfShares());
            statement.setDouble(3, dto.getValuePaid());
            statement.execute();
            return dto;
        } catch (SQLException e) {
            logger.error("ERROR: failed to insert new quote", e);
        }
        return null;
    }

    @Override
    public Position findById(ID id) {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id.getId());
            ResultSet resultSet = statement.executeQuery();
            Position position = new Position();
            while (resultSet.next()) {
                position.setId(new ID(resultSet.getInt("id")));
                position.setSymbol(resultSet.getString("symbol"));
                position.setNumOfShares(resultSet.getInt("number_of_shares"));
                position.setValuePaid(resultSet.getDouble("value_paid"));
            }
            return position;
        } catch (SQLException e) {
            logger.error("ERROR: failed to find quote by id", e);
        }
        return null;
    }

    @Override
    public List<Position> findAll() {
        try (PreparedStatement statement = this.connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<Position> positionList = new ArrayList<Position>();
            while (resultSet.next()) {
                Position tempPosition = new Position();
                tempPosition.setId(new ID(resultSet.getInt("id")));
                tempPosition.setSymbol(resultSet.getString("symbol"));
                tempPosition.setNumOfShares(resultSet.getInt("number_of_shares"));
                tempPosition.setValuePaid(resultSet.getDouble("value_paid"));
                positionList.add(tempPosition);
            }
            return positionList;
        } catch (SQLException e) {
            logger.error("ERROR: failed to find quote by id", e);
        }
        return null;
    }

    @Override
    public Position update(Position dto) {
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
