package Repository;

import Exception.ADTException;
import Exception.MyException;
import Model.ADT.IMyList;
import Model.ADT.MyList;
import Model.State.PrgState;
import Model.Statement.IStmt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Repository implements IRepository {
    private final IMyList<PrgState> states;
    private IStmt originalProgram;
    private String fileName;

    public Repository(PrgState prgState, String fileName) throws IOException, MyException {
        this.originalProgram = prgState.getOriginalProgram();
        this.fileName = fileName;
        states = new MyList<>();
        File file = new File(fileName);
        file.createNewFile();
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write("");
        }
        catch (IOException e) {
            throw new MyException(e.getMessage());
        }
    }

    public Repository() {
        states = new MyList<>();
    }

    public Repository(String givenFile) {
        this.fileName = givenFile;
        states = new MyList<>();
    }

    @Override
    public IMyList<PrgState> getPrgList() {
        return this.states;
    }

    @Override
    public void addState(PrgState state) {
        this.states.add(state);
    }


    @Override
    public PrgState getCurrentProgram() throws MyException, ADTException{
        try {
            PrgState state = this.states.getFromPosition(0);
            this.states.removeFromPosition(0);
            return state;
        }
        catch (NoSuchElementException e) {
            throw new MyException("No program states! ");
        }
    }

    @Override
    public IStmt getOriginalProgram() {
        return this.originalProgram;
    }

    @Override
    public void printPrgState(PrgState prgState) throws MyException, IOException {
        File file = new File(fileName);
        file.createNewFile();
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(prgState + "\n");
            fileWriter.close();
        }
        catch (IOException e) {
            throw new MyException(e.getMessage());
        }
    }

    }
