package com.springapp.dao.generic;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unchecked")
public interface GenericDAO <T, PK extends Serializable> {
    public void add(T instance);
    public T get(PK id);
    public void update(T object);
    public void delete(PK id);
    public <T> List<T> getAll();
}
