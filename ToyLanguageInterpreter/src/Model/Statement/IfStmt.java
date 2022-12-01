package Model.Statement;

import Exception.ExprException;
import Exception.StmtException;
import Model.ADT.IMyDictionary;
import Model.ADT.IMyStack;
import Model.State.PrgState;
import Model.Expression.IExp;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class IfStmt implements IStmt {
    private final IExp expression;
    private final IStmt ifStmt;
    private final IStmt elseStmt;

    public IfStmt(IExp expression, IStmt ifStmt, IStmt elseStmt) {
        this.expression = expression;
        this.ifStmt = ifStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public PrgState execute(PrgState state) throws StmtException, ExprException {
        IMyStack<IStmt> stack = state.getExecutionStack();
        IMyDictionary<String, IValue> table = state.getSymbolTable();
        IValue condition = this.expression.evaluate(table);
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
    public IStmt deepCopy() {
        return new IfStmt(expression, ifStmt, elseStmt);
    }

}
