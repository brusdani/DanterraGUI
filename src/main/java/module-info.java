module cz.vse.danterragui {
    requires javafx.controls;
    requires javafx.fxml;


    opens cz.vse.danterragui to javafx.fxml;
    exports cz.vse.danterragui;
}