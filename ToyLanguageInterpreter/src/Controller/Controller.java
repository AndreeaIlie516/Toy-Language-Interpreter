package Controller;

import Exception.ADTException;
import Exception.ExprException;
import Exception.MyException;
import Model.ADT.*;
import Model.State.ProgramState;
import Exception.StmtException;
import Model.Expression.ArithExp;
import Model.Expression.ValueExp;
import Model.Expression.VariableExp;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.*;
import Repository.IRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller implements IController{
    private final IRepository repository;

    public Controller(IRepository repo) {
        this.repository = repo;
    }

    public ProgramState executeOneStep(ProgramState state) throws MyException, ADTException, StmtException, ExprException {
        IMyStack<IStatement> stack = state.getExecutionStack();
        if (stack.isEmpty()) {
            throw new MyException("Stack is empty");
        }
        IStatement currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void executeAllSteps() throws MyException, IOException, ADTException {
        ProgramState prg = repository.getCurrentProgram();
        repository.printPrgState(prg);
        IMyHeap<IValue> heap = new MyHeap<>();
        //System.out.println(prg);

        while (!prg.getExecutionStack().isEmpty()) {
            try {
                executeOneStep(prg);
                //System.out.println(prg);
                repository.printPrgState(prg);

                heap.setContent(safeGarbageCollector(
                        getAddrFromSymTable(
                                prg.getSymbolTable().getContent().values(),
                                prg.getHeap().getContent().values()
                        ),
                        prg.getHeap().getContent()
                ));
                prg.setHeap(heap);
            } catch (MyException | ADTException | StmtException | ExprException exception) {
                throw new MyException(exception.getMessage());
            }
        }
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

    public void example() {
        IMyStack<IStatement> stack = new MyStack<>();
        IStatement example_1 = new CompStatement(
                new VarDeclStatement("x", new IntType()),
                new CompStatement(
                        new AssignStatement("x", new ValueExp(new IntValue(17))),
                        new PrintStatement(new VariableExp("x"))
                )
        );

        IStatement example_2 = new CompStatement(
                new VarDeclStatement("x" , new IntType()),
                new CompStatement(new AssignStatement("x", new ArithExp(
                                        new ValueExp(new IntValue(3)),
                                        new ArithExp(
                                                new ValueExp(new IntValue(5)), new ValueExp(new IntValue(7)), '*'
                                        ),
                                        '+'
                                    )
                             ),
                        new PrintStatement(new VariableExp("x"))
                )
        );

        IStatement example_3 = new CompStatement(
                new VarDeclStatement("s" , new BoolType()),
                new CompStatement(new VarDeclStatement("x", new IntType()),
                        new CompStatement(
                                new AssignStatement("s", new ValueExp(new BoolValue(true))),
                                new CompStatement(
                                    new IfStatement(
                                            new VariableExp("s"),
                                            new AssignStatement("x", new ValueExp(new IntValue(20))),
                                            new AssignStatement("x", new ValueExp(new IntValue(2)))
                                    ),
                                    new PrintStatement(new VariableExp("x"))
                                )
                        )
                )
        );

        IStatement example_4 = new CompStatement(
                new VarDeclStatement("fileName", new StringType()),
                new CompStatement(new AssignStatement("fileName", new ValueExp(new StringValue("test.txt"))),
                        new CompStatement(new OpenRFileStatement(new VariableExp("fileName")),
                                new CompStatement(new VarDeclStatement("x", new IntType()),
                                        new CompStatement(new ReadFileStatement(new VariableExp("fileName"), "x"),
                                                new CompStatement(new PrintStatement(new VariableExp("x")),
                                                        new CompStatement(new ReadFileStatement(new VariableExp("fileName"), "x"),
                                                                new CompStatement(new PrintStatement(new VariableExp("x")),
                                                                        new CloseRFileStatement(new VariableExp("fileName"))))))))));

        stack.push(example_4);
        stack.push(example_3);
        stack.push(example_2);
        stack.push(example_1);
        ProgramState state = new ProgramState(stack, new MyDictionary<String, IValue>(), new MyList<IValue>());
        System.out.println(state);
        repository.addState(state);
    }

}
