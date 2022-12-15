package Repository;

import Exception.ADTException;
import Exception.MyException;
import Model.ADT.IMyList;
import Model.State.ProgramState;
import Model.Statement.IStatement;

import java.io.IOException;

public interface IRepository {
    IMyList<ProgramState> getPrgList();
    void addState(ProgramState state);
    ProgramState getCurrentProgram() throws MyException, ADTException;

    IStatement getOriginalProgram();

    void printPrgState(ProgramState programState) throws MyException, IOException;
}

