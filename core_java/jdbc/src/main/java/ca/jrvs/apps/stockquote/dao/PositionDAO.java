package ca.jrvs.apps.stockquote.dao;

import java.util.List;

public class PositionDAO implements CrudDAO<Position, ID> {

    @Override
    public Position create(Position dto) {
        return null;
    }

    @Override
    public Position findById(long id) {
        return null;
    }

    @Override
    public List<Position> findAll() {
        return List.of();
    }

    @Override
    public Position update(Position dto) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
