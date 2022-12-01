package Repository;

import Exception.ADTException;
import Exception.MyException;
import Model.ADT.IMyList;
import Model.State.PrgState;
import Model.Statement.IStmt;

import java.io.IOException;
import java.util.List;

public interface IRepository {
    IMyList<PrgState> getPrgList();
    void addState(PrgState state);
    PrgState getCurrentProgram() throws MyException, ADTException;

    IStmt getOriginalProgram();

    void printPrgState(PrgState prgState) throws MyException, IOException;
}

