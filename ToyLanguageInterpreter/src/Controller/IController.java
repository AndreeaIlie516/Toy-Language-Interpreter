package Controller;

import Exception.MyException;
import Exception.ADTException;
import Exception.InterpreterException;
import Exception.StmtException;
import Model.State.ProgramState;

import java.io.IOException;

public interface IController {
    //ProgramState executeOneStep(ProgramState state) throws MyException, ADTException, StmtException, ExprException;
    void executeAllSteps() throws MyException, ADTException, IOException, InterpreterException;

}
