package ca.jrvs.apps.stockquote.dao;

import java.sql.Connection;
import java.util.List;

public class QuoteDAO implements CrudDAO<Quote, ID> {

    public QuoteDAO(Connection connection) {

    }

    @Override
    public Quote create(Quote dto) {
        return null;
    }

    @Override
    public Quote findById(long id) {
        return null;
    }

    @Override
    public List<Quote> findAll() {
        return List.of();
    }

    @Override
    public Quote update(Quote dto) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
