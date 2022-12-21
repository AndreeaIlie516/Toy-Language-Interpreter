import Model.ADT.*;
import Model.Expression.*;
import Model.Type.ReferenceType;
import Test.testFile;
import Controller.Controller;
import Model.State.ProgramState;
import Exception.MyException;
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
        IMyStack<IStatement> stack1 = new MyStack<>();
        IMyStack<IStatement> stack2 = new MyStack<>();
        IMyStack<IStatement> stack3 = new MyStack<>();
        IMyStack<IStatement> stack4 = new MyStack<>();
        IMyStack<IStatement> stack5 = new MyStack<>();
        IMyStack<IStatement> stack6 = new MyStack<>();
        IMyStack<IStatement> stack10 = new MyStack<>();

        IStatement example_1 = new CompStatement(
                new VarDeclStatement("x", new IntType()),
                new CompStatement(
                        new AssignStatement("x", new ValueExp(new IntValue(17))),
                        new PrintStatement(new VariableExp("x"))
                )
        );

        ProgramState prg1 = new ProgramState(stack1, new MyDictionary<String, IValue>(),  new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), example_1);
        IRepository repo1 = new Repository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);

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
        ProgramState prg2= new ProgramState(stack2, new MyDictionary<String, IValue>(),  new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), example_2);
        IRepository repo2 = new Repository(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2);

        IStatement example_3 = new CompStatement(new VarDeclStatement("a", new IntType()),
                new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new AssignStatement("a", new ValueExp(new IntValue(10))),
                        new CompStatement(new IfStatement(new RelationalExp(new VariableExp("a"),new VariableExp("v"), "<"),
                                new AssignStatement("v", new ValueExp(new IntValue(2))), new AssignStatement("v", new ValueExp(new IntValue(3)))),
                                new PrintStatement(new VariableExp("v"))))));

        ProgramState prg3 = new ProgramState(stack3, new MyDictionary<String, IValue>(),  new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), example_3);
        IRepository repo3 = new Repository(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3);

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

        ProgramState prg4 = new ProgramState(stack4, new MyDictionary<String, IValue>(),  new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), example_4);
        IRepository repo4 = new Repository(prg4, "log4.txt");
        Controller ctr4 = new Controller(repo4);

        IStatement example_5 = new CompStatement(new VarDeclStatement("v", new ReferenceType(new IntType())),
                new CompStatement(new AllocateHeapStatement("v", new ValueExp(new IntValue(20))),
                        new CompStatement(new VarDeclStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompStatement(new AllocateHeapStatement("a", new VariableExp("v")),
                                        new CompStatement(new AllocateHeapStatement("v", new ValueExp(new IntValue(30))),
                                                new PrintStatement(new ReadHeapExpression( new ReadHeapExpression(new VariableExp("a")))))))));


        ProgramState prg5 = new ProgramState(stack5, new MyDictionary<String, IValue>(),  new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), example_5);
        IRepository repo5 = new Repository(prg5, "log5.txt");
        Controller ctr5 = new Controller(repo5);

        IStatement example_6 = new CompStatement(new VarDeclStatement("x", new IntType()),
                new CompStatement(new AssignStatement("x", new ValueExp(new IntValue(10))),
                        new CompStatement(new WhileStatement(new RelationalExp(new VariableExp("x"), new ValueExp(new IntValue(0)), ">"),
                                new CompStatement(new PrintStatement(new VariableExp("x")), new AssignStatement("x", new ArithExp(new VariableExp("x"), new ValueExp(new IntValue(1)), '-')))),
                                new PrintStatement(new VariableExp("x")))));



        ProgramState prg6 = new ProgramState(stack6, new MyDictionary<String, IValue>(),  new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), example_6);
        IRepository repo6 = new Repository(prg6, "log6.txt");
        Controller ctr6 = new Controller(repo6);

        IStatement example_10 = new CompStatement(new VarDeclStatement("v", new IntType()),
                    new CompStatement(new VarDeclStatement("a", new ReferenceType(new IntType())),
                            new CompStatement(new AssignStatement("v", new ValueExp(new IntValue(10))),
                                    new CompStatement(new AllocateHeapStatement("a", new ValueExp(new IntValue(22))),
                                            new CompStatement(new ForkStatement(
                                                    new CompStatement(new WriteHeapStatement("a", new ValueExp(new IntValue(30))),
                                                            new CompStatement(new AssignStatement("v", new ValueExp(new IntValue(32))),
                                                                    new CompStatement(new PrintStatement(new VariableExp("v")),
                                                                            new PrintStatement(new ReadHeapExpression(new VariableExp("a"))))))),
                                                    new CompStatement(new PrintStatement(new VariableExp("v")),
                                                            new PrintStatement(new ReadHeapExpression(new VariableExp("a")))))))));

        ProgramState prg10 = new ProgramState(stack10, new MyDictionary<String, IValue>(),  new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), new MyHeap<>(), example_10);
        IRepository repo10 = new Repository(prg10, "log10.txt");
        Controller ctr10 = new Controller(repo10);

        TextMenu menu = new TextMenu();

        repo1.addState(prg1);
        repo2.addState(prg2);
        repo3.addState(prg3);
        repo4.addState(prg4);
        repo5.addState(prg5);
        repo6.addState(prg6);
        repo10.addState(prg10);


        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1",example_1.toString(),ctr1));
        menu.addCommand(new RunExample("2",example_2.toString(),ctr2));
        menu.addCommand(new RunExample("3",example_3.toString(),ctr3));
        menu.addCommand(new RunExample("4",example_4.toString(),ctr4));
        menu.addCommand(new RunExample("5",example_5.toString(),ctr5));
        menu.addCommand(new RunExample("6",example_6.toString(),ctr6));
        menu.addCommand(new RunExample("10",example_10.toString(),ctr10));
        menu.show();
    }
}