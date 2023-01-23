package Model.Statement;

import Exception.ADTException;
import Exception.ExprException;
import Exception.StmtException;
import Exception.TypeException;
import Model.ADT.IMyDictionary;
import Model.ADT.IMyHeap;
import Model.ADT.IMyStack;
import Model.ADT.MyDictionary;
import Model.State.ProgramState;
import Model.Expression.IExp;
import Model.Type.BoolType;
import Model.Type.IType;
import Model.Value.BoolValue;
import Model.Value.IValue;


import java.util.Map;

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
        return null;

    }

    @Override
    public String toString() {
        return "if (" + expression + ") then {" + ifStmt + "} else {" + elseStmt + "}";
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(expression, ifStmt, elseStmt);
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
            ifStmt.typeCheck(clone(table));
            elseStmt.typeCheck(clone(table));
            return table;
        } else {
            throw new TypeException("Condition not of type bool");
        }
    }
}
