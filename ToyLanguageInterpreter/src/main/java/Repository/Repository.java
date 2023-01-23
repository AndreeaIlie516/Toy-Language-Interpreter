package Repository;

import Exception.ADTException;
import Exception.MyException;
import Model.ADT.IMyList;
import Model.ADT.MyList;
import Model.State.ProgramState;
import Model.Statement.IStatement;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Repository implements IRepository {
    private List<ProgramState> states;
    private IStatement originalProgram;
    private String fileName;

    public Repository(ProgramState programState, String fileName) throws IOException, MyException {
        this.originalProgram = programState.getOriginalProgram();
        this.fileName = fileName;
        states = new LinkedList<>();
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
        states = new LinkedList<>();
    }

    public Repository(String givenFile) {
        this.fileName = givenFile;
        states = new LinkedList<>();
    }

    @Override
    public List<ProgramState> getProgramList() {
        return this.states;
    }

    @Override
    public void addState(ProgramState state) {
        this.states.add(state);
    }


    /*@Override
    public ProgramState getCurrentProgram() throws MyException, ADTException{
        try {
            ProgramState state = this.states.get(0);
            this.states.remove(0);
            return state;
        }
        catch (NoSuchElementException e) {
            throw new MyException("No program states! ");
        }
    }
    */


    @Override
    public IStatement getOriginalProgram() {
        return this.originalProgram;
    }

    @Override
    public void logProgramStateExecution(ProgramState programState) throws MyException, IOException {
        File file = new File(fileName);
        file.createNewFile();
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(programState + "\n");
            fileWriter.flush();
            if (programState.getExecutionStack().isEmpty()) {
                fileWriter.close();
                this.states.remove(0);
            }
        }
        catch (IOException e) {
            throw new MyException(e.getMessage());
        }

    }

    @Override
    public void setProgramList(List<ProgramState> programList)
    {
        this.states = programList;
    }

    @Override
    public ProgramState getProgramStateWithID(int currentID)
    {
        for(ProgramState program : states)
            if(program.getStateID() == currentID)
                return program;
        return null;
    }
}
