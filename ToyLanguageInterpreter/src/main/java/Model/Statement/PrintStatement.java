package Model.Statement;
import Exception.ExprException;
import Model.ADT.IMyDictionary;
import Model.ADT.IMyHeap;
import Model.ADT.IMyList;
import Model.ADT.IMyStack;
import Model.State.ProgramState;
import Exception.StmtException;
import Exception.TypeException;
import Model.Expression.IExp;
import Model.Type.IType;
import Model.Value.IValue;

public class PrintStatement implements IStatement {
    private final IExp expression;

    public PrintStatement(IExp expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "print(" + this.expression + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression);
    }

    @Override
    public ProgramState execute(ProgramState state) throws StmtException, ExprException {
        IMyDictionary<String, IValue> table = state.getSymbolTable();
        IMyList<IValue> output = state.getOutput();
        IMyStack<IStatement> stack = state.getExecutionStack();
        IMyHeap<IValue> heap = state.getHeap();
        IValue value = this.expression.evaluate(table, heap);
        output.add(value);
        state.setExecutionStack(stack);
        state.setOutput(output);
        return null;
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> table) throws TypeException {
        expression.typeCheck(table);
        return table;
    }
}

