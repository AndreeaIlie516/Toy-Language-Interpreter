package Model.Type;

import Model.Value.IntValue;
import Model.Value.IValue;

public class IntType implements IType {

    @Override
    public IValue getDefaultValue() {
        return new IntValue(0);
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof IntType;
    }

    @Override
    public IType deepCopy() {
        return new IntType();
    }

    @Override
    public String toString() {
        return "int";
    }
}
