package Model.Expression;

import Model.ADT.IMyDictionary;
import Exception.ExprException;
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
    public IValue evaluate(IMyDictionary<String, IValue> symbolTable) throws ExprException {
        IValue leftValue = left.evaluate(symbolTable);
        IValue rightValue = right.evaluate(symbolTable);

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
}
