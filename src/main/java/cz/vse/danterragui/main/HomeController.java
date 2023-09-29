package cz.vse.danterragui.main;

import cz.vse.danterragui.logika.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HomeController{
    @FXML
    private TextField playerInput;
    @FXML
    private Button enterButton;
    @FXML
    private TextArea textAreaOutput;
    @FXML
    private ListView<Prostor> exitPanel;
    @FXML
    private TextArea npcDialogue;
    @FXML
    private ImageView npcImage;
    @FXML
    private ImageView player;

    private IHra hra = new Hra();

    private ObservableList<Prostor> exitList = FXCollections.observableArrayList();

    private Map<String, Point2D> roomCoordinates = new HashMap<>();

    private Image aibaImage = new Image("file:///C:/Users/Daniel/Pictures/Danterra_Pictures/Aiba.jpg");
    //private Image aibaImage = new Image(getClass().getResource("Prostory/cellar.jpg").toExternalForm());
    private Image ghostImage = new Image("file:///C:/Users/Daniel/Pictures/Danterra_Pictures/Ghost.jpg");

    @FXML
    private void initialize(){
        textAreaOutput.appendText(hra.vratUvitani()+"\n");
        textAreaOutput.appendText(hra.poem()+"\n");
        textAreaOutput.appendText(hra.intro()+"\n");
        Platform.runLater(() -> playerInput.requestFocus());
        exitPanel.setItems(exitList);
        setPlayerStartingLocation();
        hra.getHerniPlan().registruj(ZmenaHry.ZMENA_MISTNOSTI,() -> {
            aktualizuj();
            updatePlayerLocation();
        });
        //hra.registruj(ZmenaHry.KONEC_HRY, () -> updateGameEnding());
        setRoomCoordinates();
        exitPanel.setCellFactory(param -> new ListCellProstor());

    }

    private void setRoomCoordinates() {
        roomCoordinates.put("hall", new Point2D(125,145));
        roomCoordinates.put("cellar", new Point2D(127,59));
        roomCoordinates.put("tower", new Point2D(23,145));
        roomCoordinates.put("treasure_room", new Point2D(14,64));
        roomCoordinates.put("gate", new Point2D(229,134));
        roomCoordinates.put("forest", new Point2D(231,217));
        roomCoordinates.put("cliffs", new Point2D(144,224));
        roomCoordinates.put("village", new Point2D(230,58));
        roomCoordinates.put("pub", new Point2D(300,56));
    }
    private void setPlayerStartingLocation(){
        player.setLayoutX(127);
        player.setLayoutY(59);
    }
    @FXML
    private void updateExitList(){
        exitList.clear();
        exitList.addAll(hra.getHerniPlan().getAktualniProstor().getVychody());
    }

    private void updatePlayerLocation(){
        String prostor = hra.getHerniPlan().getAktualniProstor().getNazev();
        player.setLayoutX(roomCoordinates.get(prostor).getX());
        player.setLayoutY(roomCoordinates.get(prostor).getY());
    }

    @FXML
    private void onEnterButtonClick(ActionEvent event){
        String command = playerInput.getText();
        playerInput.clear();
        processCommand(command);
        hra.getHerniPlan().getAktualniProstor().registruj(ZmenaHry.STAV_HRY,() -> aktualizuj());
        exitPanel.refresh();
    }

    private void processCommand(String command) {
        textAreaOutput.appendText(">"+ command + "\n");
        String result = hra.zpracujPrikaz(command);
        //textAreaOutput.appendText(result+"\n");
        npcDialogue.clear();
        npcDialogue.appendText(result);
        if(hra.getAiba().isSummoned()) {
            npcImage.setImage(aibaImage);
        }
        handleNpcDialogue(command,result,npcImage);

        updateGameEnding();
    }

    private void handleNpcDialogue(String command, String result, ImageView imageView){
        if (command.startsWith("talkTo")) {
            String npcName = command.substring("talkTo ".length());
            Npc npc = hra.getHerniPlan().getRavi();

            if (npc != null && !result.equals("Nikdo takový tu není")) {
                imageView.setImage(ghostImage);
                //imageView.setImage(null);
                //imageView.setImage(npc.getImage());
                //npcLabel.setText(npcName);
            }
        }
    }

    private void updateGameEnding() {
        if(hra.konecHry()){
            textAreaOutput.appendText(hra.ending());
            textAreaOutput.appendText(hra.vratEpilog());
            playerInput.setDisable(true);
            enterButton.setDisable(true);
            exitPanel.setDisable(true);
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
    @FXML
    public void newGame(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to restart the game?");
        Optional <ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            clearEverything();
            hra = new Hra();
            initialize();
        }
    }
    public void clearEverything(){
        textAreaOutput.clear();
        npcDialogue.clear();
        npcImage.setImage(null);
        exitList.clear();
    }
    @FXML
    private void helpClick(ActionEvent event){
        Stage helpStage = new Stage();
        WebView webView = new WebView();
        Scene helpScene = new Scene(webView);
        webView.getEngine().load(getClass().getResource("help.html").toExternalForm());
        helpStage.setScene(helpScene);
        helpStage.show();
    }


    public void aktualizuj() {
        //System.out.printf("Aktualizuji");
        if (hra.getHerniPlan().getAktualniProstor().isWasScanned()){
            updateExitList();
        } else
            exitList.clear();

    }
    @FXML
    private void clickExitPanel(MouseEvent mouseEvent){
        Prostor destination = exitPanel.getSelectionModel().getSelectedItem();
        if (destination == null) return;
        String command = PrikazJdi.NAZEV + " " + destination;
        processCommand(command);
        hra.getHerniPlan().getAktualniProstor().registruj(ZmenaHry.STAV_HRY,() -> aktualizuj());
        exitPanel.refresh();

    }

}
