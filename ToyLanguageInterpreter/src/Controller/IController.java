package Controller;

import Exception.MyException;
import Exception.ADTException;
import Exception.ExprException;
import Exception.StmtException;
import Model.State.PrgState;

import java.io.IOException;

public interface IController {
    PrgState executeOneStep(PrgState state) throws MyException, ADTException, StmtException, ExprException;
    void executeAllSteps() throws MyException, ADTException, IOException;

}
