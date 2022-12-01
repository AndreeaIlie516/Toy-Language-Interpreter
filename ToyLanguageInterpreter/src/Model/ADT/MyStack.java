package Model.ADT;

import Exception.ADTException;

import java.util.Stack;

public class MyStack<T> implements IMyStack<T> {
    private final Stack<T> myStack;

    public MyStack() {
        this.myStack = new Stack<T>();
    }

    @Override
    public T pop() throws ADTException {
        if (this.myStack.isEmpty()) {
            throw new ADTException("Stack is empty!");
        }
        return this.myStack.pop();
    }

    @Override
    public T top() throws ADTException {
        if (this.myStack.isEmpty()) {
            throw new ADTException("Stack is empty!");
        }
        return this.myStack.peek();
    }

    @Override
    public void push(T value) {

        this.myStack.push(value);
    }

    @Override
    public int size() {
        return this.myStack.size();
    }

    @Override
    public boolean isEmpty() {
        return this.myStack.isEmpty();
    }
}
