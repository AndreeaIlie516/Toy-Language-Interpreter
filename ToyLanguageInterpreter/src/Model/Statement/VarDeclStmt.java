package Model.Statement;

import Exception.ADTException;
import Model.ADT.IMyDictionary;
import Model.ADT.IMyStack;
import Model.State.PrgState;
import Exception.StmtException;
import Model.Type.IType;
import Model.Value.IValue;

public class VarDeclStmt implements IStmt{
    private final String ID;
    private final IType type;
    public VarDeclStmt(String id, IType t){
        this.ID = id;
        this.type = t;
    }

    public String getID() {
        return ID;
    }

    public IType getType() {
        return type;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ADTException {
        IMyStack<IStmt> stack = state.getExecutionStack();
        IMyDictionary<String, IValue> table = state.getSymbolTable();
        if(table.isDefined(this.ID)) {
            throw new StmtException("Variable " + this.ID + " is already defined");
        }
        table.add(this.ID, this.type.getDefaultValue());
        state.setExecutionStack(stack);
        state.setSymbolTable(table);
        return state;
    }

    @Override
    public String toString(){
        return this.type.toString() + " " + this.ID;
    }

    @Override
    public IStmt deepCopy() {
        return new VarDeclStmt(ID, type);
    }
}