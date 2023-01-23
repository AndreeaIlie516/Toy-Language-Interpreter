package Model.Statement;

import Model.ADT.IMyDictionary;
import Model.State.ProgramState;
import Exception.StmtException;
import Exception.TypeException;
import Model.Type.IType;

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

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> table) throws TypeException {
        return table;
    }
}
