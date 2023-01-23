package Model.State;

import Model.ADT.*;
import Model.Statement.IStatement;
import Model.Value.StringValue;
import Model.Value.IValue;
import Exception.ADTException;
import Exception.ExecutionException;
import Exception.ExprException;
import Exception.MyException;
import Exception.StmtException;
import Exception.TypeException;

import java.io.BufferedReader;

public class ProgramState {
    private IMyDictionary<String, IValue> symbolTable;
    private IMyStack<IStatement> executionStack;
    private IMyList<IValue> output;
    private IMyDictionary<StringValue, BufferedReader> fileTable;
    private IStatement originalProgram;
    private IMyHeap<IValue> heap;

    private static Integer lastID = 1;
    private Integer stateID;

    IMyLatchTable<Integer> latchTable;

    public ProgramState(IMyStack<IStatement> executionStack, IMyDictionary<String, IValue> symbolTable, IMyList<IValue> output, IMyDictionary<StringValue, BufferedReader> fileTable, IMyHeap<IValue> heap, IMyLatchTable<Integer> latchTable, IStatement originalProgram) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram;
        this.heap = heap;
        this.latchTable = latchTable;
        stateID = 1;
        if (originalProgram != null) {
            executionStack.push(originalProgram);
        }
    }

    public ProgramState(IMyStack<IStatement> executionStack, IMyDictionary<String, IValue> symbolTable, IMyList<IValue> output, IMyDictionary<StringValue, BufferedReader> fileTable, IMyHeap<IValue> heap, IMyLatchTable<Integer> latchTable) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.latchTable = latchTable;
    }

    public IMyStack<IStatement> getExecutionStack() {
        return this.executionStack;
    }

    public void setExecutionStack(IMyStack<IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public IMyDictionary<String, IValue> getSymbolTable() {
        return this.symbolTable;
    }
    public void setSymbolTable(IMyDictionary<String, IValue> symbolTable) {
        this.symbolTable = symbolTable;
    }
    public IMyList<IValue> getOutput() {
        return this.output;
    }

    public void setOutput(IMyList<IValue> output) {
        this.output = output;
    }

    public IStatement getOriginalProgram() {
        return this.originalProgram;
    }

    public void setOriginalProgram(IStatement originalProgram) {
        this.originalProgram = originalProgram;
    }

    public boolean isNotCompleted() {
        return !this.executionStack.isEmpty();
    }
    public IMyDictionary<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public void setFileTable(IMyDictionary<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public IMyHeap<IValue> getHeap() {
        return heap;
    }

    public void setHeap(IMyHeap<IValue> heap) {
        this.heap = heap;
    }

    public Integer getStateID() {
        return stateID;
    }
    public static synchronized int getNewProgramStateID() {
        lastID++;
        return lastID;
    }
    public synchronized void setID() {
        lastID++;
        stateID = lastID;
    }

    public IMyLatchTable<Integer> getLatchTable() {
        return latchTable;
    }

    public void setLatchTable(IMyLatchTable<Integer> latchTable) {
        this.latchTable = latchTable;
    }

    public ProgramState oneStep() throws ExecutionException, ADTException, ExprException, MyException, StmtException {
        if(executionStack.isEmpty()) {
            throw new ExecutionException("Stack is empty");
        }
        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }



    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Program state\n");
        str.append("ID: ").append(stateID.toString()).append("\n");
        str.append("Execution Stack: ").append(executionStack).append("\n");
        str.append("Symbol Table: ").append(symbolTable).append("\n");
        str.append("Heap: ").append(heap).append("\n");
        str.append("Output Console: ").append(output).append("\n");
        str.append("File Table: ").append(fileTable).append("\n");
        return str.toString();
    }

    public void typeCheck() throws TypeException, ADTException {
        originalProgram.typeCheck(new MyDictionary<>());
    }

}