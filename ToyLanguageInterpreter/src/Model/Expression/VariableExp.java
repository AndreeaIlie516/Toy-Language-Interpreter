package Model.Expression;

import Exception.ExprException;
import Exception.TypeException;
import Model.ADT.IMyDictionary;
import Model.ADT.IMyHeap;
import Model.Type.IType;
import Model.Value.IValue;

public class VariableExp implements IExp {
    private final String ID;

    public VariableExp(String ID) {
        this.ID = ID;
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> symbolTable, IMyHeap<IValue> heap) throws ExprException {
        if (!symbolTable.isDefined(ID))
            throw new ExprException("The variable is not defined for VariableExpression!");
        return symbolTable.lookup(ID);
    }
    @Override
    public String toString() {
        return ID;
    }

    @Override
    public IExp deepCopy() {
        return new VariableExp(ID);
    }

    @Override
    public IType typeCheck(IMyDictionary<String, IType> table) throws TypeException{
        return table.lookup(ID);
    }
}

