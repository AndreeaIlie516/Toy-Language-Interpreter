package Model.Statement;
import Exception.ExprException;
import Model.ADT.IMyDictionary;
import Model.ADT.IMyList;
import Model.ADT.IMyStack;
import Model.State.PrgState;
import Exception.StmtException;
import Model.Expression.IExp;
import Model.Value.IValue;

public class PrintStmt implements IStmt{
    private final IExp expression;

    public PrintStmt(IExp expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "print(" + this.expression + ")";
    }

    @Override
    public IStmt deepCopy() {
        return new PrintStmt(expression);
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException {
        IMyDictionary<String, IValue> table = state.getSymbolTable();
        IMyList<IValue> output = state.getOutput();
        IMyStack<IStmt> stack = state.getExecutionStack();
        IValue value = this.expression.evaluate(table);
        output.add(value);
        state.setExecutionStack(stack);
        state.setOutput(output);
        return state;
    }
}

