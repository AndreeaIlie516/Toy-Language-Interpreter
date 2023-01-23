package Model.Type;

import Model.Value.StringValue;
import Model.Value.IValue;

public class StringType implements IType {

    @Override
    public IValue getDefaultValue() {
        return new StringValue("");
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) return true;
        return another != null && getClass() == another.getClass();
    }

    @Override
    public IType deepCopy() {
        return new StringType();
    }

    @Override
    public String toString() {
        return "string";
    }
}
