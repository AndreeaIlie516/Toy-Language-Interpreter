package Model.State;

import Model.ADT.*;
import Model.Statement.IStmt;
import Model.Value.StringValue;
import Model.Value.IValue;

import java.io.BufferedReader;

public class PrgState {
    private IMyDictionary<String, IValue> symbolTable;
    private IMyStack<IStmt> executionStack;
    private IMyList<IValue> output;
    private IMyDictionary<StringValue, BufferedReader> fileTable;
    private IStmt originalProgram;

    public PrgState(IMyStack<IStmt> executionStack, IMyDictionary<String, IValue> symbolTable, IMyList<IValue> output, IMyDictionary<StringValue, BufferedReader> fileTable, IStmt originalProgram) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram;
    }

    public PrgState(IMyStack<IStmt> executionStack, IMyDictionary<String, IValue> symbolTable, IMyList<IValue> output) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
    }

    public IMyStack<IStmt> getExecutionStack() {
        return this.executionStack;
    }

    public void setExecutionStack(IMyStack<IStmt> executionStack) {
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

    public IStmt getOriginalProgram() {
        return this.originalProgram;
    }

    public void setOriginalProgram(IStmt originalProgram) {
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

    @Override
    public String toString() {
        return "PrgState{" +
                "symbolTable=" + symbolTable +
                ", executionStack=" + executionStack +
                ", output=" + output +
                ", fileTable=" + fileTable +
                ", originalProgram=" + originalProgram +
                '}';
    }
}