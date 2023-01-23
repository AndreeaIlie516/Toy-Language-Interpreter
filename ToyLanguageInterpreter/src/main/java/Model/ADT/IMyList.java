package Model.ADT;

import Exception.ADTException;

import java.util.List;

public interface IMyList<T> {
    void add(T value);

    void remove(T value) throws ADTException;

    int size();

    T getFromPosition(int index) throws ADTException;

    void removeFromPosition(int index) throws ADTException;

    boolean containsValue(T value);

    List<T> getContent();

    boolean isEmpty();
}
