package Model.Statement;

import Exception.StmtException;
import Model.ADT.IMyStack;
import Model.State.PrgState;

public class CompStmt implements IStmt {
    private final IStmt first;
    private final IStmt second;

    public CompStmt(IStmt f, IStmt s) {
        first = f;
        second = s;
    }

    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    public PrgState execute(PrgState state) throws StmtException {
        IMyStack<IStmt> stack = state.getExecutionStack();
        stack.push(second);
        stack.push(first);
        state.setExecutionStack(stack);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new CompStmt(first.deepCopy(), second.deepCopy());
    }
}

