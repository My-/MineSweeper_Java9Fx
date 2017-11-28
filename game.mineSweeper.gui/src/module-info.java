module game.mineSweeper.gui {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    requires game.mineSweeper.core;
    requires java.logging;

    exports game.mineSweeper.gui;
}