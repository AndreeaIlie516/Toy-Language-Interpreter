package Model.Statement;

import Model.State.PrgState;
import Exception.StmtException;

public class NopStmt implements IStmt{
    @Override
    public String toString() {
        return "nop";
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException {
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new NopStmt();
    }
}
