module cz.vse.danterragui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens cz.vse.danterragui.main to javafx.fxml;
    exports cz.vse.danterragui.main;
}