package Model.State;

import Model.ADT.*;
import Model.Statement.IStatement;
import Model.Value.StringValue;
import Model.Value.IValue;
import com.sun.jdi.Value;

import java.io.BufferedReader;

public class ProgramState {
    private IMyDictionary<String, IValue> symbolTable;
    private IMyStack<IStatement> executionStack;
    private IMyList<IValue> output;
    private IMyDictionary<StringValue, BufferedReader> fileTable;
    private IStatement originalProgram;
    private IMyHeap<IValue> heap;

    public ProgramState(IMyStack<IStatement> executionStack, IMyDictionary<String, IValue> symbolTable, IMyList<IValue> output, IMyDictionary<StringValue, BufferedReader> fileTable, IMyHeap<IValue> heap, IStatement originalProgram) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram;
        this.heap = heap;
        executionStack.push(originalProgram);
    }

    public ProgramState(IMyStack<IStatement> executionStack, IMyDictionary<String, IValue> symbolTable, IMyList<IValue> output, IMyDictionary<StringValue, BufferedReader> fileTable, IStatement originalProgram) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram;
        this.heap = new MyHeap<IValue>();
    }

    public ProgramState(IMyStack<IStatement> executionStack, IMyDictionary<String, IValue> symbolTable, IMyList<IValue> output) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.heap = new MyHeap<IValue>();
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

    public boolean isCompleted() {
        return this.executionStack.isEmpty();
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

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Program state\n");
        str.append("Execution Stack: ").append(executionStack).append(" \n");
        str.append("Symbol Table: ").append(symbolTable).append(" \n");
        str.append("Heap: ").append(heap).append(" \n");
        str.append("Output Console: ").append(output).append(" \n");
        str.append("File Table: ").append(fileTable).append(" \n");
        return str.toString();
    }

}