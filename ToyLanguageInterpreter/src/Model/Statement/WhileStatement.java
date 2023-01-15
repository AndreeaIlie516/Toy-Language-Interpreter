package Model.Statement;

import Model.ADT.IMyDictionary;
import Model.ADT.IMyStack;
import Model.ADT.MyDictionary;
import Model.Expression.IExp;
import Model.State.ProgramState;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Exception.ADTException;
import Exception.MyException;
import Exception.ExprException;
import Exception.TypeException;

import java.util.Map;

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

    private static IMyDictionary<String, IType> clone(IMyDictionary<String, IType> table) throws TypeException, ADTException {
        IMyDictionary<String, IType> newSymbolTable = new MyDictionary<>();
        for (Map.Entry<String, IType> entry: table.getContent().entrySet()) {
            newSymbolTable.add(entry.getKey(), entry.getValue());
        }
        return newSymbolTable;
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> table) throws TypeException, ADTException {
        IType expressionType = expression.typeCheck(table);
        if (expressionType.equals(new BoolType())) {
            statement.typeCheck(clone(table));
            return table;
        } else {
            throw new TypeException("Condition not of type bool");
        }
    }
}
