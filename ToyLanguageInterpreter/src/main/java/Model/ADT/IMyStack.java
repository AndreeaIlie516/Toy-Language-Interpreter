package Model.ADT;

import Exception.ADTException;

import java.util.List;

public interface IMyStack<T> {
    T pop() throws ADTException;

    T top() throws ADTException;

    void push(T value);

    int size();

    boolean isEmpty();

    List<T> getContent();
}
