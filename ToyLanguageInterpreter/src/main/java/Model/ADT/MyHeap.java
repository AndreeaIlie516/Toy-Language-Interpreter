package Model.ADT;

import Exception.ADTException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MyHeap<V> implements IMyHeap<V> {
    private Map<Integer, V> heap;
    private final AtomicInteger freeLocation;

    public MyHeap() {
        this.heap = new ConcurrentHashMap<Integer, V>();
        this.freeLocation = new AtomicInteger(0);
    }

    @Override
    public int allocate(V value) {
        int newLocation = freeLocation.incrementAndGet();
        heap.put(newLocation, value);
        return newLocation;
    }

    @Override
    public void update(int address, V value) {
        heap.put(address, value);
    }

    @Override
    public V get(int address) {
        return heap.get(address);
    }

    @Override
    public void deallocate(int address) {
        heap.remove(address);
    }

    @Override
    public boolean contains(int address) {
        return heap.containsKey(address);
    }

    @Override
    public Map<Integer, V> getContent() {
        return heap;
    }

    @Override
    public void setContent(Map<Integer, V> content) {
        heap = content;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        for (Map.Entry<Integer, V> el : heap.entrySet()) {
            content.append(el.getKey()).append("-").append(el.getValue()).append(" ");
        }
        return content.toString();
    }

    @Override
    public void add(Integer ID, V value) throws ADTException {
        if (heap.containsKey(ID)) {
            throw new ADTException("Element already exists");
        }
        heap.put(ID, value);
    }
}