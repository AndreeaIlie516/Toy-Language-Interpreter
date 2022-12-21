package Model.Statement;

import Exception.StmtException;
import Model.ADT.IMyStack;
import Model.State.ProgramState;

public class CompStatement implements IStatement {
    private final IStatement first;
    private final IStatement second;

    public CompStatement(IStatement f, IStatement s) {
        first = f;
        second = s;
    }

    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    public ProgramState execute(ProgramState state) throws StmtException {
        IMyStack<IStatement> stack = state.getExecutionStack();
        stack.push(second);
        stack.push(first);
        state.setExecutionStack(stack);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CompStatement(first.deepCopy(), second.deepCopy());
    }
}

