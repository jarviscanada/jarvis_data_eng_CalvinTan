package ca.jrvs.apps.stockquote.dao;

import java.util.List;

public interface CrudDAO<T, E> {

    public abstract T create(T dto);
    public abstract T findById(E id);
    public abstract List<T> findAll();
    public abstract T update(T dto);
    public abstract void delete(E id);
}
