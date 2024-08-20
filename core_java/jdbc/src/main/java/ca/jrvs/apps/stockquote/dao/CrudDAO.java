package ca.jrvs.apps.stockquote.dao;

import java.util.List;

public interface CrudDAO<T, ID> {

    public abstract T create(T dto);
    public abstract T findById(long id);
    public abstract List<T> findAll();
    public abstract T update(T dto);
    public abstract void delete(long id);
}
