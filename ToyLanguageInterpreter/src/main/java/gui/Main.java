package gui;

import Model.Expression.*;
import Model.Statement.*;
import Model.Type.IntType;
import Model.Type.ReferenceType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    ChooseProgramController chooseProgramController;

    @Override
    public void start(Stage primaryStage) {
        try {
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChooseProgramStyle.fxml"));
            GridPane root = (GridPane) loader.load();
            chooseProgramController = loader.getController();
            addStatementsToController();
            chooseProgramController.setMenuStage(primaryStage);
            Scene scene = new Scene(root, screenBounds.getWidth() / 3, screenBounds.getHeight());
            scene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
            primaryStage.setTitle("Toy Language Menu");
            primaryStage.setScene(scene);
            primaryStage.setX(0);
            primaryStage.setY(0);

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addStatementsToController() {
        chooseProgramController.addStatement(example1);
        chooseProgramController.addStatement(example2);
        chooseProgramController.addStatement(example3);
        chooseProgramController.addStatement(example4);
        chooseProgramController.addStatement(example5);
        chooseProgramController.addStatement(example6);
        chooseProgramController.addStatement(example10);
        chooseProgramController.addStatement(example11);
    }

    private static final IStatement example1 = new CompStatement(
            new VarDeclStatement("x", new IntType()),
            new CompStatement(
                    new AssignStatement("x", new ValueExp(new IntValue(17))),
                    new PrintStatement(new VariableExp("x"))
            )
    );

    private static final IStatement example2 = new CompStatement(
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

    private static final IStatement example3 = new CompStatement(new VarDeclStatement("a", new IntType()),
            new CompStatement(new VarDeclStatement("v", new IntType()), new CompStatement(new AssignStatement("a", new ValueExp(new IntValue(10))),
                    new CompStatement(new IfStatement(new RelationalExp(new VariableExp("a"),new VariableExp("v"), "<"),
                            new AssignStatement("v", new ValueExp(new IntValue(2))), new AssignStatement("v", new ValueExp(new IntValue(3)))),
                            new PrintStatement(new VariableExp("v"))))));

    private static final  IStatement example4 = new CompStatement(
            new VarDeclStatement("fileName", new StringType()),
            new CompStatement(new AssignStatement("fileName", new ValueExp(new StringValue("test.txt"))),
                    new CompStatement(new OpenRFileStatement(new VariableExp("fileName")),
                            new CompStatement(new VarDeclStatement("x", new IntType()),
                                    new CompStatement(new ReadFileStatement(new VariableExp("fileName"), "x"),
                                            new CompStatement(new PrintStatement(new VariableExp("x")),
                                                    new CompStatement(new ReadFileStatement(new VariableExp("fileName"), "x"),
                                                            new CompStatement(new PrintStatement(new VariableExp("x")),
                                                                    new CloseRFileStatement(new VariableExp("fileName"))))))))));

    IStatement example5 = new CompStatement(new VarDeclStatement("v", new ReferenceType(new IntType())),
            new CompStatement(new AllocateHeapStatement("v", new ValueExp(new IntValue(20))),
                    new CompStatement(new VarDeclStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                            new CompStatement(new AllocateHeapStatement("a", new VariableExp("v")),
                                    new CompStatement(new AllocateHeapStatement("v", new ValueExp(new IntValue(30))),
                                            new PrintStatement(new ReadHeapExpression( new ReadHeapExpression(new VariableExp("a")))))))));

    IStatement example6 = new CompStatement(new VarDeclStatement("x", new IntType()),
            new CompStatement(new AssignStatement("x", new ValueExp(new IntValue(10))),
                    new CompStatement(new WhileStatement(new RelationalExp(new VariableExp("x"), new ValueExp(new IntValue(0)), ">"),
                            new CompStatement(new PrintStatement(new VariableExp("x")), new AssignStatement("x", new ArithExp(new VariableExp("x"), new ValueExp(new IntValue(1)), '-')))),
                            new PrintStatement(new VariableExp("x")))));

    IStatement example10 = new CompStatement(new VarDeclStatement("v", new IntType()),
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

    IStatement example11 = new CompStatement(
            new VarDeclStatement(
                    "v",
                    new IntType()
            ),
            new CompStatement(
                    new AssignStatement(
                            "v",
                            new ValueExp(
                                    new BoolValue(true)
                            )
                    ),
                    new PrintStatement(
                            new VariableExp(
                                    "v"
                            )
                    )
            )
    );

    public static void main(String[] args) {
        launch(args);
    }
}