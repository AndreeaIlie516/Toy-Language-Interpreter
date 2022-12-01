package Model.Type;

import Model.Value.IValue;

public interface IType {
    IValue getDefaultValue();

    boolean equals(Object another);

    IType deepCopy();
    String toString();
}
