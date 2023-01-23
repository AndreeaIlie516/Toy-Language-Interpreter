package Model.ADT;

import Exception.ADTException;

import java.util.List;
import java.util.Map;

public interface IMyDictionary<K, V> {
    void add(K key, V value) throws ADTException;

    void update(K key, V value);

    void remove(K key) throws ADTException;

    V lookup(K key);

    boolean isDefined(K key);

    Map<K, V> getContent();

    int getSize();

    List<K> getKeys();

    List<V> getValues();
}
