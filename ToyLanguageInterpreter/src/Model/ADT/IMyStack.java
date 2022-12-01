package Model.ADT;

import Exception.ADTException;

public interface IMyStack<T> {
    T pop() throws ADTException;

    T top() throws ADTException;

    void push(T value);

    int size();

    boolean isEmpty();
}
