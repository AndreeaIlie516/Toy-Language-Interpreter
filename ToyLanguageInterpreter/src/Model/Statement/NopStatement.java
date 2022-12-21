package Model.Statement;

import Model.State.ProgramState;
import Exception.StmtException;

public class NopStatement implements IStatement {
    @Override
    public String toString() {
        return "nop";
    }

    @Override
    public ProgramState execute(ProgramState state) throws StmtException {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NopStatement();
    }
}
