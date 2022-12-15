package Model.Type;

import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.ReferenceValue;

public class ReferenceType implements IType {
    private final IType inner;

    public ReferenceType() {
        inner = new IntType();
    }

    public ReferenceType(IType inner) {
        this.inner = inner;
    }

    public IType getInner() {
        return inner;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ReferenceType) {
            return inner.equals(((ReferenceType) o).getInner());
        }
        else {
            return false;
        }
    }
    @Override
    public IValue getDefaultValue() {
        return new ReferenceValue(0, inner);
    }

    @Override
    public IType deepCopy() {
        return new ReferenceType(inner.deepCopy());
    }


    public String toString() {
        return "Ref (" + inner.toString() + ")";
    }
}
