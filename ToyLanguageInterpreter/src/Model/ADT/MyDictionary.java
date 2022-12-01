package Model.ADT;

import Exception.ADTException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDictionary<K, V> implements IMyDictionary<K, V> {
    private final Map<K, V> myDictionary;

    public MyDictionary() {
        this.myDictionary = new HashMap<>();
    }

    @Override
    public void add(K key, V value) throws ADTException {
        if (this.myDictionary.containsKey(key)) {
            throw new ADTException("Key already exists!");
        }
        this.myDictionary.put(key, value);
    }

    @Override
    public void update(K key, V value) {
        this.myDictionary.put(key, value);
    }

    @Override
    public void remove(K key) throws ADTException {
        if (!this.myDictionary.containsKey(key)) {
            throw new ADTException("Key does not exist!");
        }
        this.myDictionary.remove(key);
    }

    @Override
    public V lookup(K key) {

        return this.myDictionary.get(key);
    }

    @Override
    public boolean isDefined(K key) {

        return this.myDictionary.containsKey(key);
    }

    @Override
    public Map<K, V> getContent() {

        return this.myDictionary;
    }

    @Override
    public int getSize() {

        return this.myDictionary.size();
    }

    @Override
    public List getKeys() {

        return List.copyOf(this.myDictionary.keySet());
    }

    @Override
    public List getValues() {

        return List.copyOf(this.myDictionary.values());
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        for (Map.Entry<K, V> el : myDictionary.entrySet()) {
            content.append(el.getKey()).append("-").append(el.getValue()).append(" ");
        }
        return content.toString();
    }
}