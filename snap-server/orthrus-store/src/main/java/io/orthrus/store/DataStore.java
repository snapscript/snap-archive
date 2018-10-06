package io.orthrus.store;

import java.util.function.Predicate;

public interface DataStore<T> {
   void save(T object);
   boolean delete(Comparable<?> key);
   boolean deleteFirst(String column, Comparable<?> value);
   boolean deleteAll(String column, Comparable<?> value);
   T find(Comparable<?> key);
   T findFirst(String column, Comparable<?> value);
   Iterable<T> findAll();
   Iterable<T> findAll(Predicate<T> filter);
   Iterable<T> findAll(String column, Comparable<?> value);
}
