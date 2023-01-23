package Model.Statement;

import Exception.ADTException;
import Exception.ExprException;
import Exception.MyException;
import Exception.StmtException;
import Exception.TypeException;
import Model.ADT.IMyDictionary;
import Model.State.ProgramState;
import Model.Type.IType;

public interface IStatement {
   ProgramState execute(ProgramState state) throws StmtException, ExprException, ADTException, MyException;

    IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnv) throws TypeException, ADTException;

    IStatement deepCopy();
}
