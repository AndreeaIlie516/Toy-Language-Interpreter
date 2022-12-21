package Model.Statement;

import Exception.ADTException;
import Model.ADT.IMyDictionary;
import Model.ADT.IMyStack;
import Model.State.ProgramState;
import Exception.StmtException;
import Model.Type.IType;
import Model.Value.IValue;

public class VarDeclStatement implements IStatement {
    private final String ID;
    private final IType type;
    public VarDeclStatement(String id, IType t){
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
    public ProgramState execute(ProgramState state) throws StmtException, ADTException {
        IMyStack<IStatement> stack = state.getExecutionStack();
        IMyDictionary<String, IValue> table = state.getSymbolTable();
        if(table.isDefined(this.ID)) {
            throw new StmtException("Variable " + this.ID + " is already defined");
        }
        table.add(this.ID, this.type.getDefaultValue());
        state.setExecutionStack(stack);
        state.setSymbolTable(table);
        return null;
    }

    @Override
    public String toString(){
        return this.type.toString() + " " + this.ID;
    }

    @Override
    public IStatement deepCopy() {
        return new VarDeclStatement(ID, type);
    }
}