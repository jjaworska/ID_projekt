package sample.dao;

import java.util.List;

public interface AbstractDao<T> {

    public T get(long id);

    public List<T> getAll();

    // void save(T t);

    // void update(T t, String[] params);

    // void delete(T t);
}

