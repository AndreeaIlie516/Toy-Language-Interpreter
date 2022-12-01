package Model.ADT;

import Exception.ADTException;

import java.util.*;

public class MyList <T> implements IMyList<T> {
    private final List<T> myList;

    public MyList() {
        this.myList = new ArrayList<>();
    }

    @Override
    public void add(T value) {
        this.myList.add(value);
    }

    @Override
    public void remove(T value) throws ADTException {
        if (!this.myList.contains(value)) {
            throw new ADTException("Value doesn't exist!");
        }
        this.myList.remove(value);
    }

    @Override
    public int size() {
        return this.myList.size();
    }

    @Override
    public T getFromPosition(int index) throws ADTException {
        if (index < 0 || index >= this.myList.size()) {
            throw new ADTException("The index is out of bounds!");
        }
        return this.myList.get(index);
    }

    @Override
    public void removeFromPosition(int index) throws ADTException {
        if (index < 0 || index >= this.myList.size()) {
            throw new ADTException("The index is out of bounds!");
        }
        this.myList.remove(index);
    }

    @Override
    public boolean containsValue(T value) {
        return this.myList.contains(value);
    }

    @Override
    public List<T> getContent() {
        return this.myList;
    }

    @Override
    public boolean isEmpty() {
        return this.myList.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        for (T elem: myList) {
            content.append(elem).append(" ");
        }
        return content.toString();
    }
}