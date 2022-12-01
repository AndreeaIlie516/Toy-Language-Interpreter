package Model.Statement;

import Exception.ADTException;
import Exception.ExprException;
import Exception.StmtException;
import Model.State.PrgState;

public interface IStmt {
   public PrgState execute(PrgState state) throws StmtException, ExprException, ADTException;

    IStmt deepCopy();
}
