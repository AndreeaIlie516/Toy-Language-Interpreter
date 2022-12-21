package Model.Statement;

import Model.ADT.IMyDictionary;
import Model.ADT.IMyStack;
import Model.ADT.MyDictionary;
import Model.ADT.MyStack;
import Model.State.ProgramState;
import Model.Value.IValue;
import Exception.ADTException;

import java.util.Map;

public class ForkStatement implements IStatement{
    IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ADTException {
        IMyDictionary<String, IValue> newSymbolTable = new MyDictionary<>();
        for (Map.Entry<String, IValue> entry: state.getSymbolTable().getContent().entrySet()) {
            newSymbolTable.add(entry.getKey(), entry.getValue().deepCopy());
        }
        IMyStack<IStatement> stack = new MyStack<>();
        stack.push(statement);
        ProgramState newProgram = new ProgramState(stack, newSymbolTable, state.getOutput(), state.getFileTable(), state.getHeap());
        newProgram.setID();
        return newProgram;
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement);
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }
}
