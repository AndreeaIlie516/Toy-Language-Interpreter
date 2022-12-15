package Model.Value;

import Model.Type.IType;
import Model.Type.ReferenceType;

public class ReferenceValue implements IValue {
    private final Integer address;
    private final IType locationType;

    public ReferenceValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public IType getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public IValue deepCopy() {
        return new ReferenceValue(address, locationType.deepCopy());
    }


    @Override
    public String toString() {
        return "(" + address +"," + locationType + ")";
    }
}
