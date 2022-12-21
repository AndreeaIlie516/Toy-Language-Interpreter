package Model.Statement;

import Model.ADT.IMyStack;
import Model.Expression.IExp;
import Model.State.ProgramState;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Exception.MyException;
import Exception.ExprException;

public class WhileStatement implements IStatement {

    IExp expression;
    IStatement statement;

    public WhileStatement(IExp expression, IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException, ExprException {
        IMyStack<IStatement> stack = state.getExecutionStack();
        IValue expressionValue = expression.evaluate(state.getSymbolTable(), state.getHeap());
        if (expressionValue.getType().equals(new BoolType())) {
            if (expressionValue.equals(new BoolValue(true))) {
                stack.push(new WhileStatement(expression, statement));
                stack.push(statement);
            }
        } else {
            throw new MyException("Expression not of type bool");
        }

        state.setExecutionStack(stack);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

    @Override
    public String toString() {
        return "(while (" + expression + ") " + statement + ")";
    }
}
