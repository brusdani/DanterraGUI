package cz.vse.danterragui.main;

import cz.vse.danterragui.logika.Hra;
import cz.vse.danterragui.logika.IHra;
import cz.vse.danterragui.logika.Prostor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class HomeController implements Pozorovatel {
    @FXML
    private TextField playerInput;
    @FXML
    private Button enterButton;
    @FXML
    private TextArea textAreaOutput;
    @FXML
    private ListView exitPanel;

    private IHra hra = new Hra();

    private ObservableList<Prostor> exitList = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        textAreaOutput.appendText(hra.vratUvitani()+"\n");
        textAreaOutput.appendText(hra.poem()+"\n");
        textAreaOutput.appendText(hra.intro()+"\n");
        Platform.runLater(() -> playerInput.requestFocus());
        exitPanel.setItems(exitList);
        hra.getHerniPlan().registruj(this);
    }
    @FXML
    private void updateExitList(){
        exitList.clear();
        exitList.addAll(hra.getHerniPlan().getAktualniProstor().getVychody());
    }

    @FXML
    private void onEnterButtonClick(ActionEvent event){
        String command = playerInput.getText();
        textAreaOutput.appendText(">"+ command + "\n");
        String result = hra.zpracujPrikaz(command);
        textAreaOutput.appendText(result+"\n");
        playerInput.clear();
        hra.getHerniPlan().getAktualniProstor().registruj(this);

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


    @Override
    public void aktualizuj() {
        //System.out.printf("Aktualizuji");
        if (hra.getHerniPlan().getAktualniProstor().isWasScanned()){
            updateExitList();
        } else
            exitList.clear();

    }
}
