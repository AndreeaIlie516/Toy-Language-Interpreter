package View;

import Controller.Controller;
import Exception.MyException;
import Exception.ADTException;
import Exception.InterpreterException;
import Repository.IRepository;
import Repository.Repository;

import java.io.IOException;

public class AppView {
    Controller controller;

    public void printMenu() {
        System.out.println("****************\n");
        System.out.println("Example_1 = int x;  x=17;   print(x)\n");
        System.out.println("Example_2 = int x;  x=3+5*7;    print(x)\n");
        System.out.println("Example_3 = bool s; int x;  s=false;    if (s == true) then {x=20} else {x=2};  print(x)\n");
        System.out.println("****************\n");
    }

    public AppView() {
        IRepository repository = new Repository("log.txt");
        this.controller = new Controller(repository);
        printMenu();
        try {
            controller.executeAllSteps();
        } catch (MyException | IOException | ADTException e) {
            System.out.println(e.getMessage());
        } catch (InterpreterException e) {
            throw new RuntimeException(e);
        }
    }
}