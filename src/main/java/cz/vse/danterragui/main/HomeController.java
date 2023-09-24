package cz.vse.danterragui.main;

import cz.vse.danterragui.logika.Hra;
import cz.vse.danterragui.logika.IHra;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class HomeController {
    @FXML
    private TextField playerInput;
    @FXML
    private Button enterButton;
    @FXML
    private TextArea textAreaOutput;

    private IHra hra = new Hra();

    @FXML
    private void initialize(){
        textAreaOutput.appendText(hra.vratUvitani()+"\n");
        Platform.runLater(() -> playerInput.requestFocus());
    }

    @FXML
    private void onEnterButtonClick(ActionEvent event){
        String command = playerInput.getText();
        textAreaOutput.appendText(">"+ command + "\n");
        String result = hra.zpracujPrikaz(command);
        textAreaOutput.appendText(result+"\n");
        playerInput.clear();

        if(hra.konecHry()){
            textAreaOutput.appendText(hra.vratEpilog());
            playerInput.setDisable(true);
            enterButton.setDisable(true);
        }
    }
    @FXML
    public void terminateGame(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to quit the game?");
        Optional <ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }
}
