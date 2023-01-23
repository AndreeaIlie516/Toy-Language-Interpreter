package gui;

import Exception.ADTException;
import Exception.InterpreterException;
import Exception.TypeException;
import Model.ADT.MyDictionary;
import Model.Statement.IStatement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class ChooseProgramController {
    private final List<IStatement> statements = new LinkedList<>();

    private Stage menuStage;

    public void setMenuStage(Stage stage) {
        menuStage = stage;
    }

    public void addStatement(IStatement statement) {
        statements.add(statement);
        programStateListView.setItems(getProgramStatesList());
    }

    @FXML
    private ListView<String> programStateListView;

    @FXML
    private void initialize() {
        programStateListView.setItems(getProgramStatesList());
    }

    private ObservableList<String> getProgramStatesList() {
        return FXCollections.observableList(statements.stream().map(Object::toString).collect(Collectors.toList()));
    }


    private void launchNewWindow(int programNumber) throws IOException, InterpreterException, TypeException, ADTException {
        IStatement statement = statements.get(programNumber - 1);
        statement.typeCheck(new MyDictionary<>());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainProgramStyle.fxml"));
        GridPane secondaryLayout = loader.load();
        MainProgramController mainProgramController = loader.getController();
        mainProgramController.setProgramState(statement, programNumber);
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Scene secondScene = new Scene(secondaryLayout, screenBounds.getWidth() * 2 / 3, screenBounds.getHeight());
        secondScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        Stage newWindow = new Stage();
        newWindow.setTitle("Program " + programNumber);
        newWindow.setScene(secondScene);
        newWindow.setX(menuStage.getX() + screenBounds.getWidth() / 3);
        newWindow.setY(menuStage.getY());
        newWindow.show();
    }

    private void raiseAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }

    public void selectProgram() {
        try {
            int index = programStateListView.getSelectionModel().getSelectedIndex();

            if (index == -1) {
                raiseAlert("Please select a program");
                return;
            }

            launchNewWindow(index + 1);
        } catch (InterpreterException e) {
            raiseAlert(e.getMessage());
            System.out.println(e.toString());
        } catch (Exception e) {
            raiseAlert(e.getMessage());
            e.printStackTrace();
            System.out.println(e.toString());
        }

    }
}