package Model.Expression;

import Model.ADT.IMyDictionary;
import Model.ADT.IMyHeap;
import Model.Type.IType;
import Model.Type.ReferenceType;
import Model.Value.IValue;
import Exception.ExprException;
import Exception.TypeException;
import Model.Value.ReferenceValue;

public class ReadHeapExpression implements IExp {
    private final IExp expression;

    public ReadHeapExpression(IExp expression) {

        this.expression = expression;
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> symbolTable, IMyHeap<IValue> heap) throws ExprException {
        IValue value = expression.evaluate(symbolTable, heap);
        if (value instanceof ReferenceValue referenceValue) {
            int address = referenceValue.getAddress();
            if (heap.contains(address)) {
                return heap.get(address);
            } else {
                throw new ExprException("The address doesn't exist in the heap");
            }
        } else {
            throw new ExprException("The expression could not be evaluated to a ReferenceValue");
        }
    }

    @Override
    public IExp deepCopy() {
        return new ReadHeapExpression(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "readHeap(" + expression.toString() + ")";
    }

    @Override
    public IType typeCheck(IMyDictionary<String, IType> table) throws TypeException {
        IType type = expression.typeCheck(table);
        if (type instanceof ReferenceType) {
            ReferenceType referenceType = (ReferenceType) type;
            return referenceType.getInner();
        } else
            throw new TypeException("Expression not of reference type.");
    }
}
