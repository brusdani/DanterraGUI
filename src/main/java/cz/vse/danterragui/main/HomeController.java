package cz.vse.danterragui.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HomeController {
    @FXML
    private TextField playerInput;
    @FXML
    private Button enterButton;
    @FXML
    private TextArea textAreaOutput;

    @FXML
    private void onEnterButtonClick(ActionEvent event){
        textAreaOutput.appendText(playerInput.getText());
        playerInput.clear();
    }
}
