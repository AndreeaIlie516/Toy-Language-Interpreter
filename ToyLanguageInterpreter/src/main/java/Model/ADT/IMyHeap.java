package Model.ADT;

import java.util.Map;
import Exception.ADTException;

public interface IMyHeap<V> {
    int allocate(V value);

    void update(int address, V value);

    V get(int address);

    void deallocate(int address);

    boolean contains(int address);

    Map<Integer, V> getContent();

    void setContent(Map<Integer, V> content);

    void add(Integer ID, V value) throws ADTException;

}