package Model.Statement;

import Exception.ExprException;
import Model.ADT.IMyDictionary;
import Model.State.PrgState;
import Exception.StmtException;
import Model.Expression.IExp;
import Model.Value.IValue;

public class AssignStmt implements IStmt {
    private final String ID;
    private final IExp expression;

    public AssignStmt(String ID, IExp expression) {
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
    public PrgState execute(PrgState state) throws StmtException, ExprException {
        IMyDictionary<String, IValue> table = state.getSymbolTable();
        IValue updatedValue = this.expression.evaluate(table);
        IValue oldValue = table.lookup(this.ID);
        if((!(table.isDefined(this.ID))) || (!oldValue.getType().equals(updatedValue.getType()))) {
            throw new StmtException("Variable " + this.ID + " is not defined or has a different type than the expression");
        }
        table.update(this.ID, updatedValue);
        return state;
    }

    @Override
    public IStmt deepCopy() {
        return new AssignStmt(ID, expression.deepCopy());
    }

}