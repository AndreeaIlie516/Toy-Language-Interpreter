package Model.Expression;

import Exception.ExprException;
import Model.ADT.IMyDictionary;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class LogicExp implements IExp {
    private final IExp leftExpression;
    private final IExp rightExpression;
    private final String operator;

    public LogicExp(IExp leftExpression, IExp rightExpression, String operator) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> symbolTable) throws ExprException {
        IValue leftValue = leftExpression.evaluate(symbolTable);
        IValue rightValue = rightExpression.evaluate(symbolTable);

        if (leftValue.getType().equals(new BoolType()) && rightValue.getType().equals(new BoolType())) {
            BoolValue leftBoolValue = (BoolValue) leftValue;
            BoolValue rightBoolValue = (BoolValue) rightValue;

            boolean leftBoolean = leftBoolValue.getValue();
            boolean rightBoolean = rightBoolValue.getValue();

            return switch (operator) {
                case "&&" -> new BoolValue(leftBoolean && rightBoolean);
                case "||" -> new BoolValue(leftBoolean || rightBoolean);
                default -> throw new ExprException("Invalid operator for LogicExpression");
            };
        } else {
            throw new ExprException("Invalid types for LogicExpression");
        }
    }

    @Override
    public String toString() {
        return leftExpression.toString() + " " + operator + " " + rightExpression.toString();
    }

    @Override
    public IExp deepCopy() {
        return new LogicExp(leftExpression.deepCopy(), rightExpression.deepCopy(), operator);
    }
}