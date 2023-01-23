package Controller;

import Exception.ADTException;
import Exception.TypeException;
import Exception.MyException;
import Model.ADT.*;
import Model.State.ProgramState;
import Model.Statement.IStatement;
import Model.Value.ReferenceValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Exception.InterpreterException;
import Model.Value.IValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller implements IController{
    private final IRepository repository;

    private Boolean displayAll;

    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repository = repo;
    }

    public void setDisplayAll(Boolean displayAll) {
        this.displayAll = displayAll;
    }
    /*
    public ProgramState executeOneStep(ProgramState state) throws MyException, ADTException, StmtException, ExprException {

        IMyStack<IStatement> stack = state.getExecutionStack();
        if (stack.isEmpty()) {
            throw new MyException("Stack is empty");
        }
        IStatement currentStmt = stack.pop();
        return currentStmt.execute(state);

    }
    */
    public void executeAllSteps() throws MyException, IOException, ADTException, InterpreterException {
        executor = Executors.newFixedThreadPool(2);

        List<ProgramState> programStateList = removeCompletedPrograms(repository.getProgramList());

        while (programStateList.size() > 0) {
            conservativeGarbageCollector(programStateList);
            oneStepForAllPrograms(programStateList);

            programStateList = removeCompletedPrograms(repository.getProgramList());
        }

        executor.shutdownNow();
        repository.setProgramList(programStateList);
    }

    Map<Integer, IValue> unsafeGarbageCollector(List<Integer> addresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(elem -> addresses.contains(elem.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    Map<Integer, IValue> safeGarbageCollector(List<Integer> addresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(elem -> addresses.contains(elem.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void oneStepForAllPrograms(List<ProgramState> states) throws InterpreterException {
        states.forEach(p -> {
            try {

                repository.logProgramStateExecution(p);
            } catch (MyException | IOException exception) {
                exception.printStackTrace();
            }
        });

        List<Callable<ProgramState>> callList = states.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStep))
                .collect(Collectors.toList());
        try {
            List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println(e.getMessage());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            states.addAll(newProgramList);
        } catch (InterruptedException e) {
            throw new InterpreterException(e.getMessage());
        }

        states.forEach(prg -> {
            try {
                repository.logProgramStateExecution(prg);
            } catch (MyException | IOException exception) {
                exception.printStackTrace();
            }
        });

        repository.setProgramList(states);
    }
    public void conservativeGarbageCollector(List<ProgramState> programStateList) {
        var heap = Objects.requireNonNull(programStateList.stream()
                .map(p -> getAddrFromSymTable(
                        p.getSymbolTable().getContent().values(),
                        p.getHeap().getContent().values()))
                .map(Collection::stream)
                .reduce(Stream::concat).orElse(null)).collect(Collectors.toList());
        programStateList.forEach(programState -> {
            programState.getHeap().setContent(
                    unsafeGarbageCollector(
                            heap,
                            programStateList.get(0).getHeap().getContent()
                    ));
        });
    }

    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues)
    {
        return symTableValues.stream()
                .filter(v -> v instanceof ReferenceValue)
                .map(v -> {
                    ReferenceValue v1 = (ReferenceValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    List<Integer> getAddrFromSymTable(Collection<IValue> symTableValues, Collection<IValue> heap) {
        return Stream.concat(
                heap.stream()
                        .filter(v -> v instanceof ReferenceValue)
                        .map(v -> {
                            ReferenceValue v1 = (ReferenceValue) v;
                            return v1.getAddress();
                        }),
                symTableValues.stream()
                        .filter(v -> v instanceof ReferenceValue)
                        .map(v -> {
                            ReferenceValue v1 = (ReferenceValue) v;
                            return v1.getAddress();
                        })
        ).collect(Collectors.toList());
    }

    List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList) {
        return inProgramList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public String displayState(ProgramState state) {
        return state.toString();
    }

    public void addState(ProgramState state) {
        repository.addState(state);
    }

    public IMyList<IValue> getOutput() {
        if (repository.getProgramList().size() == 0) {
            return new MyList<>();
        }
        return repository.getProgramList().get(0).getOutput();
    }

    public void typeCheck() throws TypeException, ADTException {
        repository.getProgramList().get(0).typeCheck();
    }

    public IMyDictionary<String, IValue> getSymbolTable(int process) {
        List<ProgramState> states = repository.getProgramList().stream().filter((el) -> el.getStateID() == process).collect(Collectors.toList());
        if (states.size() == 0) {
            return new MyDictionary<>();
        } else {
            return states.get(0).getSymbolTable();
        }
    }

    public IMyHeap<IValue> getHeapTable() {
        if (repository.getProgramList().size() == 0) {
            return new MyHeap<>();
        }
        return repository.getProgramList().get(0).getHeap();
    }

    public IMyDictionary<StringValue, BufferedReader> getFileTable() {
        if (repository.getProgramList().size() == 0) {
            return new MyDictionary<>();
        }
        return repository.getProgramList().get(0).getFileTable();
    }
    public IMyLatchTable<Integer> getLatchTable() {
        if (repository.getProgramList().size() == 0) {
            return new MyLatchTable<>();
        }
        return repository.getProgramList().get(0).getLatchTable();
    }

    public IMyStack<IStatement> getExecutionStack(int process) {
        List<ProgramState> states = repository
                .getProgramList()
                .stream()
                .filter((el) -> el.getStateID() == process)
                .collect(Collectors.toList());
        if (states.size() == 0) {
            return new MyStack<>();
        } else {
            return states.get(0).getExecutionStack();
        }
    }

    public Integer numberOfProgramStates() {
        return repository.getProgramList().size();
    }

    public void openExecutor() {
        executor = Executors.newFixedThreadPool(2);
    }

    public void closeExecutor() {
        executor.shutdownNow();
    }

    public void setFinalStateList(List<ProgramState> programStateList) {
        repository.setProgramList(programStateList);
    }

    public List<ProgramState> getStateList() {
        return removeCompletedPrograms(repository.getProgramList());
    }

}
