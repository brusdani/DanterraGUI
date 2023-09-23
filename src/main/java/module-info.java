module cz.vse.danterragui {
    requires javafx.controls;
    requires javafx.fxml;


    opens cz.vse.danterragui.main to javafx.fxml;
    exports cz.vse.danterragui.main;
}