package Model.Statement;

import Exception.ExprException;
import Exception.TypeException;
import Model.ADT.IMyDictionary;
import Model.ADT.IMyHeap;
import Model.State.ProgramState;
import Exception.StmtException;
import Model.Expression.IExp;
import Model.Type.IType;
import Model.Value.IValue;

public class AssignStatement implements IStatement {
    private final String ID;
    private final IExp expression;

    public AssignStatement(String ID, IExp expression) {
        this.ID = ID;
        this.expression = expression;
    }

    public String getID() {
        return ID;
    }

    public IExp getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return ID + " = " + expression.toString();
    }

    @Override
    public ProgramState execute(ProgramState state) throws StmtException, ExprException {
        IMyDictionary<String, IValue> table = state.getSymbolTable();
        IMyHeap<IValue> heap = state.getHeap();
        IValue updatedValue = this.expression.evaluate(table, heap);
        IValue oldValue = table.lookup(this.ID);
        if((!(table.isDefined(this.ID))) || (!oldValue.getType().equals(updatedValue.getType()))) {
            throw new StmtException("Variable " + this.ID + " is not defined or has a different type than the expression");
        }
        table.update(this.ID, updatedValue);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(ID, expression.deepCopy());
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> table) throws TypeException {
        IType typeVar = table.lookup(ID);
        IType typeExpr = expression.typeCheck(table);
        if (typeVar.equals(typeExpr))
            return table;
        else
            throw new TypeException("Not the same type on assignment.");
    }

}