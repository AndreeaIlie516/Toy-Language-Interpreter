package Model.Expression;

import Model.ADT.IMyDictionary;
import Exception.ExprException;
import Exception.TypeException;
import Model.ADT.IMyHeap;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.IValue;

public class RelationalExp implements IExp {
    private final IExp left;
    private final IExp right;
    private final String operator;

    public RelationalExp(IExp left, IExp right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> symbolTable, IMyHeap<IValue> heap) throws ExprException {
        IValue leftValue = left.evaluate(symbolTable, heap);
        IValue rightValue = right.evaluate(symbolTable, heap);

        if (leftValue.getType().equals(new IntType())) {
            if (rightValue.getType().equals(new IntType())) {
                IntValue leftIntValue = (IntValue) leftValue;
                IntValue rightIntValue = (IntValue) rightValue;

                int leftInt = leftIntValue.getValue();
                int rightInt = rightIntValue.getValue();

                switch (operator) {
                    case "<":
                        return new BoolValue(leftInt < rightInt);
                    case "<=":
                        return new BoolValue(leftInt <= rightInt);
                    case "==":
                        return new BoolValue(leftInt == rightInt);
                    case "!=":
                        return new BoolValue(leftInt != rightInt);
                    case ">":
                        return new BoolValue(leftInt > rightInt);
                    case ">=":
                        return new BoolValue(leftInt >= rightInt);
                    default:
                        throw new ExprException("Invalid operator");
                }
            } else {
                throw new ExprException("Right operand is not an integer");
            }
        } else {
            throw new ExprException("Left operand is not an integer");
        }
    }

    @Override
    public String toString() {
        return left.toString() + " " + operator + " " + right.toString();
    }

    @Override
    public IExp deepCopy() {
        return new RelationalExp(left.deepCopy(), right.deepCopy(), operator);
    }

    @Override
    public IType typeCheck(IMyDictionary<String, IType> table) throws TypeException {
        IType type1, type2;
        type1 = left.typeCheck(table);
        type2 = right.typeCheck(table);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new BoolType();
            } else
                throw new TypeException("Second operand is not an integer.");
        } else
            throw new TypeException("First operand is not an integer.");
    }
}
