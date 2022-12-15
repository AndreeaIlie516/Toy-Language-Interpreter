import Test.testFile;
import Controller.Controller;
import Model.ADT.IMyStack;
import Model.ADT.MyDictionary;
import Model.ADT.MyList;
import Model.ADT.MyStack;
import Model.State.PrgState;
import Exception.MyException;
import Model.Expression.ArithExp;
import Model.Expression.RelationalExp;
import Model.Expression.ValueExp;
import Model.Expression.VariableExp;
import Model.Statement.*;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.IValue;
import Repository.IRepository;
import Repository.Repository;
import View.*;

import java.io.BufferedReader;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, MyException {
        IMyStack<IStmt> stack1 = new MyStack<>();
        IMyStack<IStmt> stack2 = new MyStack<>();
        IMyStack<IStmt> stack3 = new MyStack<>();
        IMyStack<IStmt> stack4 = new MyStack<>();

        testFile test = new testFile();
        test.testFileOperation();
        System.out.println("Tests passed");

        IStmt example_1 = new CompStmt(
                new VarDeclStmt("x", new IntType()),
                new CompStmt(
                        new AssignStmt("x", new ValueExp(new IntValue(17))),
                        new PrintStmt(new VariableExp("x"))
                )
        );

        PrgState prg1 = new PrgState(stack1, new MyDictionary<String, IValue>(),  new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), example_1);
        IRepository repo1 = new Repository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);

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
        PrgState prg2= new PrgState(stack2, new MyDictionary<String, IValue>(),  new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), example_2);
        IRepository repo2 = new Repository(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2);

        IStmt example_3 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(10))),
                        new CompStmt(new IfStmt(new RelationalExp(new VariableExp("a"),new VariableExp("v"), "<"),
                                new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                new PrintStmt(new VariableExp("v"))))));

        PrgState prg3 = new PrgState(stack3, new MyDictionary<String, IValue>(),  new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), example_3);
        IRepository repo3 = new Repository(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3);

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

        PrgState prg4 = new PrgState(stack4, new MyDictionary<String, IValue>(),  new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), example_4);
        IRepository repo4 = new Repository(prg4, "log4.txt");
        Controller ctr4 = new Controller(repo4);

        TextMenu menu = new TextMenu();

        repo1.addState(prg1);
        repo2.addState(prg2);
        repo3.addState(prg3);
        repo4.addState(prg4);


        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",example_1.toString(),ctr1));
        menu.addCommand(new RunExample("2",example_2.toString(),ctr2));
        menu.addCommand(new RunExample("3",example_3.toString(),ctr3));
        menu.addCommand(new RunExample("4",example_4.toString(),ctr4));
        menu.show();
    }
}