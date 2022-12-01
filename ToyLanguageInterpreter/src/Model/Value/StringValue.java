package Model.Value;

import Model.Type.StringType;
import Model.Type.IType;

import java.util.Objects;

public class StringValue implements IValue {
    private String value;

    public StringValue() {

        this.value = "";
    }
    public StringValue(String value) {

        this.value = value;
    }

    @Override
    public IType getType() {

        return new StringType();
    }

    @Override
    public String toString() {

        return this.value;
    }

    public String getValue() {

        return this.value;
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) return true;
        if (another == null || getClass() != another.getClass()) return false;
        StringValue that = (StringValue) another;
        return Objects.equals(value, that.value);
    }

    @Override
    public void setValue(IValue value) {
        if (value instanceof StringValue) {
            this.value = ((StringValue) value).getValue();
        }
    }

    @Override
    public IValue deepCopy() {

        return new StringValue(value);
    }
}