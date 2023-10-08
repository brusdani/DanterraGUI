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
import javafx.stage.Modality;
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
    @FXML
    private ListView<Thing> inventoryPanel;
    @FXML
    private Label npcLabel;
    @FXML
    private ListView<Thing> roomPanel;
    @FXML
    private Button answerButton;
    @FXML
    private TextArea answerOutput;
    @FXML
    private TextField answerInput;
    @FXML
    private ListView<Npc> npcPanel;
    @FXML
    private Button aibaButton;
    @FXML
    private Button okButton;

    private IHra hra = new Hra();

    private ObservableList<Prostor> exitList = FXCollections.observableArrayList();
    private ObservableList<Thing> inventory = FXCollections.observableArrayList();

    private ObservableList<Thing> roomContents = FXCollections.observableArrayList();

    private ObservableList<Npc> roomNpcs = FXCollections.observableArrayList();

    private NpcImageView npcImageView = new NpcImageView();

    private Map<String, Point2D> roomCoordinates = new HashMap<>();

    private Image aibaImage = new Image("file:///C:/Users/Daniel/Pictures/Danterra_Pictures/Aiba.jpg");

    @FXML
    private void initialize(){
        textAreaOutput.appendText(hra.vratUvitani()+"\n");
        textAreaOutput.appendText(hra.poem()+"\n");
        textAreaOutput.appendText(hra.intro()+"\n");
        Platform.runLater(() -> playerInput.requestFocus());
        exitPanel.setItems(exitList);
        inventoryPanel.setItems(inventory);
        roomPanel.setItems(roomContents);
        npcPanel.setItems(roomNpcs);
        setPlayerStartingLocation();
        registerStavHryObserver();
        hra.getHerniPlan().registruj(ZmenaHry.ZMENA_MISTNOSTI,() -> {
            updateExitPanel();
            updatePlayerLocation();
            updateNpcList();
            updateRiddle();
            updateRoomContents();
            registerStavHryObserver();
        });
        hra.getInventory().registruj(ZmenaHry.STAV_INVENTARE, () -> {
            updateInventory();
            updateRoomContents();
        });
        hra.registruj(ZmenaHry.KONEC_HRY, () -> updateGameEnding());
        setRoomCoordinates();
        exitPanel.setCellFactory(param -> new ListCellProstor());
        inventoryPanel.setCellFactory(param -> new ListCellThing());
        roomPanel.setCellFactory(param -> new ListCellThing());
        npcPanel.setCellFactory(param -> new ListCellNpc());

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
    @FXML
    private void updateInventory() {
        inventory.clear();
        inventory.addAll(hra.getInventory().getItemsList());
    }
    @FXML
    private void updateRoomContents(){
        roomContents.clear();
        if(hra.getHerniPlan().getAktualniProstor().isWasScanned()) {
            roomContents.addAll(hra.getHerniPlan().getAktualniProstor().getThings());
        }
    }
    @FXML
    private void updateNpcList(){
        roomNpcs.clear();
        if(hra.getHerniPlan().getAktualniProstor().isWasScanned()) {
            roomNpcs.addAll(hra.getHerniPlan().getAktualniProstor().getNpcs());
        }
    }

    @FXML
    private void updateRiddle(){
        answerOutput.clear();
        if(hra.getHerniPlan().getAktualniProstor().isWasScanned()) {
            if (hra.getHerniPlan().getAktualniProstor().getRiddle() != null) {
                answerOutput.setText(hra.getHerniPlan().getAktualniProstor().getRiddle().getRiddle());
            } else {
                answerOutput.setText("There's no riddle in this room");
            }
        }
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
        //updateInventory();
        if (hra.getHerniPlan().getAktualniProstor().isWasScanned()){
            //updateRoomContents();
            //updateRiddle();
            //updateNpcList();
        } else {
            roomContents.clear();
            answerOutput.clear();
            roomNpcs.clear();
        }
        //hra.getHerniPlan().getAktualniProstor().registruj(ZmenaHry.STAV_HRY,() -> aktualizuj());
        exitPanel.refresh();
        inventoryPanel.refresh();
        roomPanel.refresh();
    }
    @FXML
    private void onAnswerButtonClick(ActionEvent event){
        String playerAnswer = "answer " + answerInput.getText();
        //String answer = answerInput.getText();
        answerInput.clear();
        processCommand(playerAnswer);
//        if(hra.getHerniPlan().getAktualniProstor().getRiddle()!=null) {
//            if (answer.equals(hra.getHerniPlan().getAktualniProstor().getRiddle().getAnswer())) {
//                updateInventory();
//                hra.getHerniPlan().getAktualniProstor().setRiddle(null);
//                updateRiddle();
//                inventoryPanel.refresh();
//            }
//        }
    }
    @FXML
    private void onAibaButtonClick(ActionEvent event){
        if (!hra.getAiba().isSummoned()) {
            String command = PrikazCallAiba.NAZEV;
            processCommand(command);
            aibaButton.setText("aibaScan");
        } else {
            String command = PrikazAibaScan.NAZEV;
            processCommand(command);
//            updateRoomContents();
//            updateRiddle();
//            updateNpcList();
//            updateExitPanel();
        }
    }
    @FXML
    private void onOkButtonClick(ActionEvent event) {
        npcDialogue.clear();
        npcImage.setImage(null);
        npcLabel.setText("");
    }

    private void processCommand(String command) {
        textAreaOutput.appendText(">"+ command + "\n");
        String result = hra.zpracujPrikaz(command);
        //textAreaOutput.appendText(result+"\n");
        npcDialogue.clear();
        npcDialogue.appendText(result);
        if(hra.getAiba().isSummoned()) {
            npcImage.setImage(aibaImage);
            npcLabel.setText("Aiba");
        }
        handleNpcDialogue(command,result,npcImage);
        updateGameEnding();
    }

    private void handleNpcDialogue(String command, String result, ImageView imageView){
        if (command.startsWith("talkTo")) {
            String npcName = command.substring("talkTo ".length());
            Image npcImage = npcImageView.getImage(npcName);

            if (npcImage != null && !result.equals("Nikdo takový tu není")) {
                imageView.setImage(npcImage);
                npcLabel.setText(npcName);
            } else if (hra.getAiba().isSummoned()) {
                imageView.setImage(aibaImage);
                npcLabel.setText("Aiba");
            } else {
                imageView.setImage(null);
                npcLabel.setText("");
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
            aibaButton.setText("Call Aiba");
            playerInput.setDisable(false);
            enterButton.setDisable(false);
            exitPanel.setDisable(false);
        }
    }
    public void clearEverything(){
        textAreaOutput.clear();
        npcDialogue.clear();
        npcImage.setImage(null);
        inventory.clear();
        exitList.clear();
        roomContents.clear();
        npcLabel.setText("");
        answerOutput.clear();
        roomNpcs.clear();
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


    public void updateExitPanel() {
        if (hra.getHerniPlan().getAktualniProstor().isWasScanned()){
            updateExitList();
        } else {
            exitList.clear();
        }
    }
    @FXML
    private void clickExitPanel(MouseEvent mouseEvent){
        Prostor destination = exitPanel.getSelectionModel().getSelectedItem();
        if (destination == null) return;
        if(destination.isLocked()){
            String destinationString = destination.toString().replace("(Locked)", "");
            String command = PrikazUnlock.NAZEV + " " + destinationString;
            processCommand(command);
            updateExitList();
            //updateInventory();
            return;
        }
        String command = PrikazJdi.NAZEV + " " + destination;
        processCommand(command);
        //if (hra.getHerniPlan().getAktualniProstor().isWasScanned()){
            //updateRoomContents();
            //updateRiddle();
        //} else {
            //roomContents.clear();
            //answerOutput.clear();
            //roomNpcs.clear();
        //}
        //hra.getHerniPlan().getAktualniProstor().registruj(ZmenaHry.STAV_INVENTARE,() -> aktualizuj());
        exitPanel.refresh();
        roomPanel.refresh();

    }
    @FXML
    private void clickRoomContents(MouseEvent mouseEvent){
        Thing targetItem = roomPanel.getSelectionModel().getSelectedItem();
        if (targetItem == null) return;
        String command = PrikazPickup.NAZEV + " " + targetItem;
        processCommand(command);
        //updateInventory();
        //updateRoomContents();
        inventoryPanel.refresh();
        roomPanel.refresh();
    }
    @FXML
    private void clickNpcPanel(MouseEvent mouseEvent){
        Npc targetNpc = npcPanel.getSelectionModel().getSelectedItem();
        if (targetNpc == null) return;
        String command = PrikazTalkTo.NAZEV + " " + targetNpc.getName();
        processCommand(command);
        updateNpcList();
        npcPanel.refresh();
    }
    @FXML
    private void showUseOrDropPopup(MouseEvent mouseEvent) {
        Thing targetItem = inventoryPanel.getSelectionModel().getSelectedItem();
        if (targetItem == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("Use or Drop Item");
        alert.setHeaderText("What would you like to do with the item?");
        alert.setContentText("Choose an option:");

        ButtonType useButton = new ButtonType("Use");
        ButtonType dropButton = new ButtonType("Drop");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(useButton, dropButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == useButton) {
                String command = PrikazUse.NAZEV + " " + targetItem;
                processCommand(command);
            } else if (result.get() == dropButton) {
                String command = PrikazDrop.NAZEV + " " + targetItem;
                processCommand(command);
            }
            updateInventory();
            updateRoomContents();
            inventoryPanel.refresh();
            roomPanel.refresh();
        }
    }
    private void registerStavHryObserver() {
        hra.getHerniPlan().getAktualniProstor().registruj(ZmenaHry.STAV_PROSTORU, () -> {
            updateExitPanel();
            updateRoomContents();
            updateRiddle();
            updateNpcList();
        });
    }
}
