package Controller;

import Exception.ADTException;
import Exception.ExprException;
import Exception.MyException;
import Model.ADT.IMyStack;
import Model.ADT.MyDictionary;
import Model.ADT.MyList;
import Model.ADT.MyStack;
import Model.State.PrgState;
import Exception.StmtException;
import Model.Expression.ArithExp;
import Model.Expression.ValueExp;
import Model.Expression.VariableExp;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.IValue;
import Repository.IRepository;

import java.io.IOException;

public class Controller implements IController{
    private IRepository repository;

    public Controller(IRepository repo) {
        this.repository = repo;
    }

    public PrgState executeOneStep(PrgState state) throws MyException, ADTException, StmtException, ExprException {
        IMyStack<IStmt> stack = state.getExecutionStack();
        if (stack.isEmpty()) {
            throw new MyException("Stack is empty");
        }
        IStmt currentStmt = stack.pop();
        return currentStmt.execute(state);
    }

    public void executeAllSteps() throws MyException, IOException, ADTException {
        PrgState prg = repository.getCurrentProgram();
        repository.printPrgState(prg);
        //System.out.println(prg);

        while (!prg.getExecutionStack().isEmpty()) {
            try {
                executeOneStep(prg);
                //System.out.println(prg);
                repository.printPrgState(prg);
            } catch (MyException | ADTException | StmtException | ExprException exception) {
                throw new MyException(exception.getMessage());
            }
        }
    }

    public void example() {
        IMyStack<IStmt> stack = new MyStack<>();
        IStmt example_1 = new CompStmt(
                new VarDeclStmt("x", new IntType()),
                new CompStmt(
                        new AssignStmt("x", new ValueExp(new IntValue(17))),
                        new PrintStmt(new VariableExp("x"))
                )
        );

        IStmt example_2 = new CompStmt(
                new VarDeclStmt("x" , new IntType()),
                new CompStmt(new AssignStmt("x", new ArithExp(
                                        new ValueExp(new IntValue(3)),
                                        new ArithExp(
                                                new ValueExp(new IntValue(5)), new ValueExp(new IntValue(7)), '*'
                                        ),
                                        '+'
                                    )
                             ),
                        new PrintStmt(new VariableExp("x"))
                )
        );

        IStmt example_3 = new CompStmt(
                new VarDeclStmt("s" , new BoolType()),
                new CompStmt(new VarDeclStmt("x", new IntType()),
                        new CompStmt(
                                new AssignStmt("s", new ValueExp(new BoolValue(true))),
                                new CompStmt(
                                    new IfStmt(
                                            new VariableExp("s"),
                                            new AssignStmt("x", new ValueExp(new IntValue(20))),
                                            new AssignStmt("x", new ValueExp(new IntValue(2)))
                                    ),
                                    new PrintStmt(new VariableExp("x"))
                                )
                        )
                )
        );

        IStmt example_4 = new CompStmt(
                new VarDeclStmt("fileName", new StringType()),
                new CompStmt(new AssignStmt("fileName", new ValueExp(new StringValue("test.txt"))),
                        new CompStmt(new OpenRFileStmt(new VariableExp("fileName")),
                                new CompStmt(new VarDeclStmt("x", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VariableExp("fileName"), "x"),
                                                new CompStmt(new PrintStmt(new VariableExp("x")),
                                                        new CompStmt(new ReadFileStmt(new VariableExp("fileName"), "x"),
                                                                new CompStmt(new PrintStmt(new VariableExp("x")),
                                                                        new CloseRFileStmt(new VariableExp("fileName"))))))))));

        stack.push(example_4);
        stack.push(example_3);
        stack.push(example_2);
        stack.push(example_1);
        PrgState state = new PrgState(stack, new MyDictionary<String, IValue>(), new MyList<IValue>());
        System.out.println(state);
        repository.addState(state);
    }

}
