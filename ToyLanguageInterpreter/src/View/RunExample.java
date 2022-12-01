package View;

import Controller.Controller;
import Exception.MyException;
import Exception.ADTException;

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
            controller.executeAllSteps();
        }
        catch (MyException | IOException | ADTException e) {
            System.out.println(e.toString());
        }
    }
}