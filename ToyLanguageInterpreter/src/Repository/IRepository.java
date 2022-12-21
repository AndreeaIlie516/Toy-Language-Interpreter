package Repository;

import Exception.ADTException;
import Exception.MyException;
import Model.State.ProgramState;
import Model.Statement.IStatement;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramList();
    void addState(ProgramState state);
    //ProgramState getCurrentProgram() throws MyException, ADTException;

    IStatement getOriginalProgram();

    void logProgramStateExecution(ProgramState programState) throws MyException, IOException;

    void setProgramList(List<ProgramState> programList);

    ProgramState getProgramStateWithID(int currentID);
}

