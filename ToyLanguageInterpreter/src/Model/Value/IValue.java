package Model.Value;

import Exception.MyException;
import Model.Type.IType;

public interface IValue {
    IType getType();

    void setValue(IValue value) throws MyException;

    boolean equals(Object another);
    String toString();

    IValue deepCopy();

}