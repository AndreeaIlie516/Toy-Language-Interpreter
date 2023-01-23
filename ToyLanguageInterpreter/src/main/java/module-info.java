module gui.toylanguageinterpretergui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens gui to javafx.fxml;
    exports gui;
}