package View;

import Controller.Controller;
import Exception.MyException;
import Exception.ADTException;
import Exception.InterpreterException;
import Exception.TypeException;

import java.io.IOException;

public class RunExample extends Command {
    private Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.typeCheck();
            controller.executeAllSteps();
        }
        catch (MyException | IOException | ADTException | InterpreterException e) {
            System.out.println(e.toString());
        } catch (TypeException e) {
            System.out.println(e.toString());
        }
    }
}