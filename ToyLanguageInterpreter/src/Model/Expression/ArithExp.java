package Model.Expression;

import Exception.ExprException;
import Model.ADT.IMyDictionary;
import Model.Type.IntType;
import Model.Value.IntValue;
import Model.Value.IValue;

public class ArithExp implements IExp {
    private final IExp expression1;
    private final IExp expression2;
    private final int operation;  //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(IExp expression1, IExp expression2, char operation) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        switch (operation) {
            case '+' -> this.operation = 1;
            case '-' -> this.operation = 2;
            case '*' -> this.operation = 3;
            case '/' -> this.operation = 4;
            default -> this.operation = 0;
        }
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> symbolTable) throws ExprException {
        IValue value1 = expression1.evaluate(symbolTable);
        IValue value2 = expression2.evaluate(symbolTable);

        if (value1.getType().equals(new IntType()) && value2.getType().equals(new IntType())) {
            IntValue intValue1 = (IntValue) value1;
            IntValue intValue2 = (IntValue) value2;
            int number1 = intValue1.getValue();
            int number2 = intValue2.getValue();

            switch (operation) {
                case 1 -> {
                    return new IntValue(number1 + number2);
                }
                case 2 -> {
                    return new IntValue(number1 - number2);
                }
                case 3 -> {
                    return new IntValue(number1 * number2);
                }
                case 4 -> {
                    if (number2 == 0) {
                        throw new ExprException("Division by zero fro ArithmeticExpression");
                    }
                    return new IntValue(number1 / number2);
                }
                default -> {
                    throw new ExprException("Invalid operation for ArithmeticExpression");
                }
            }
        } else {
            throw new ExprException("Invalid types for ArithmeticExpression");
        }
    }

    @Override
    public String toString() {
        return switch(operation) {
            case 1 -> expression1.toString() + " + " + expression2.toString();
            case 2 -> expression1.toString() + " - " + expression2.toString();
            case 3 -> expression1.toString() + " * " + expression2.toString();
            case 4 -> expression1.toString() + " / " + expression2.toString();
            default -> "";
        };
    }

    @Override
    public IExp deepCopy() {
        return new ArithExp(expression1.deepCopy(), expression2.deepCopy(), switch (operation) {
            case 1 -> '+';
            case 2 -> '-';
            case 3 -> '*';
            case 4 -> '/';
            default -> ' ';
        });
    }
}
