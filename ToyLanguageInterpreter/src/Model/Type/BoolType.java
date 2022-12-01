package Model.Type;

import Model.Value.BoolValue;
import Model.Value.IValue;

public class BoolType implements IType {

    @Override
    public IValue getDefaultValue() {
        return new BoolValue(false);
    }

    @Override
    public boolean equals(Object another) {
        return another instanceof BoolType;
    }

    @Override
    public IType deepCopy() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return "boolean";
    }
}
