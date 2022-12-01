package Model.Value;

import Exception.MyException;
import Model.Type.IntType;
import Model.Type.IType;

public class IntValue implements IValue {
    private int value;

    public IntValue() {
        this.value = 0;
    }
    public IntValue(int value) {
        this.value = value;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public boolean equals(Object another) {
        if (this == another)
            return true;
        if (another instanceof IntValue) {
            return value == ((IntValue) another).getValue();
        }
        return false;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    public int getValue() {
        return value;
    }

    @Override
    public void setValue(IValue value) throws MyException {
        if (value instanceof IntValue) {
            this.value = ((IntValue) value).getValue();
        }
        else {
            throw new MyException("Invalid value");
        }
    }

    @Override
    public IValue deepCopy() {
        return new IntValue(value);
    }
}
