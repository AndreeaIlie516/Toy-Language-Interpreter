package Model.Statement;

import Exception.ADTException;
import Exception.ExprException;
import Exception.MyException;
import Exception.StmtException;
import Model.State.ProgramState;

public interface IStatement {
   ProgramState execute(ProgramState state) throws StmtException, ExprException, ADTException, MyException;

    IStatement deepCopy();
}
