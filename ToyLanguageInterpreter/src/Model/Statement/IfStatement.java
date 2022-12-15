package Model.Statement;

import Exception.ExprException;
import Exception.StmtException;
import Model.ADT.IMyDictionary;
import Model.ADT.IMyHeap;
import Model.ADT.IMyStack;
import Model.State.ProgramState;
import Model.Expression.IExp;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class IfStatement implements IStatement {
    private final IExp expression;
    private final IStatement ifStmt;
    private final IStatement elseStmt;

    public IfStatement(IExp expression, IStatement ifStmt, IStatement elseStmt) {
        this.expression = expression;
        this.ifStmt = ifStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public ProgramState execute(ProgramState state) throws StmtException, ExprException {
        IMyStack<IStatement> stack = state.getExecutionStack();
        IMyDictionary<String, IValue> table = state.getSymbolTable();
        IMyHeap<IValue> heap = state.getHeap();
        IValue condition = this.expression.evaluate(table, heap);
        if(!condition.getType().equals(new BoolType())) {
            throw new StmtException("Condition is not a boolean");
        }
        if(((BoolValue) condition).getValue()) {
            stack.push(this.ifStmt);
        } else {
            stack.push(this.elseStmt);
        }
        state.setExecutionStack(stack);
        return state;

    }

    @Override
    public String toString() {
        return "if (" + expression + ") then {" + ifStmt + "} else {" + elseStmt + "}";
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(expression, ifStmt, elseStmt);
    }

}
