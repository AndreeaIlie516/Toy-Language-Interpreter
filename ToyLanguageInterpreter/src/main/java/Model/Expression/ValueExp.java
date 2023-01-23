package Model.Expression;

import Exception.ExprException;
import Exception.TypeException;
import Model.ADT.IMyDictionary;
import Model.ADT.IMyHeap;
import Model.Type.IType;
import Model.Value.IValue;

public class ValueExp implements IExp {
    private final IValue value;

    public ValueExp(IValue value) {
        this.value = value;
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> symbolTable, IMyHeap<IValue> heap) throws ExprException {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public IExp deepCopy() {
        return new ValueExp(value.deepCopy());
    }

    @Override
    public IType typeCheck(IMyDictionary<String, IType> table) throws TypeException {
        return value.getType();
    }
}
