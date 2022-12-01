package Model.Expression;

import Exception.ExprException;
import Model.ADT.IMyDictionary;
import Model.Value.IValue;

public class ValueExp implements IExp {
    private final IValue value;

    public ValueExp(IValue value) {
        this.value = value;
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> symbolTable) throws ExprException {
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
}
