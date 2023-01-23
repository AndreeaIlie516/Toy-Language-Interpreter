package Model.ADT;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MyLatchTable<T> implements IMyLatchTable<T> {

    AtomicInteger freeValue;
    Map<Integer, T> latchTable;

    public MyLatchTable() {
        freeValue = new AtomicInteger(0);
        latchTable = new HashMap<>();
    }

    @Override
    public synchronized int allocate(T value) {
        latchTable.put(freeValue.incrementAndGet(), value);
        return freeValue.get();
    }

    @Override
    public synchronized void update(int address, T value) {
        latchTable.put(address, value);
    }

    @Override
    public synchronized Map<Integer, T> getContent() {
        return latchTable;
    }

    @Override
    public synchronized boolean exists(int address) {
        return latchTable.containsKey(address);
    }

    @Override
    public synchronized void setContent(Map<Integer, T> map) {
        latchTable = map;
    }

    @Override
    public synchronized T get(int addr) {
        return latchTable.get(addr);
    }
}