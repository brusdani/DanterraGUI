package cz.vse.danterragui.main;

import cz.vse.danterragui.logika.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
/**
 * Class HomeController - Controller class for the game's graphical user interface (GUI).
 * Handles user interactions and updates the game state accordingly.
 * @author Daniel Brus
 * @version 1.0 , November 2023
 */

public class HomeController {
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
    private ImageView minimap;
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
    @FXML
    private Button sailButton;
    @FXML
    private Button fireIVButton;
    @FXML
    private TabPane tabPane;
    private BooleanProperty isButtonVisible = new SimpleBooleanProperty(false);

    private IHra hra = new Hra();

    private ObservableList<Prostor> exitList = FXCollections.observableArrayList();
    private ObservableList<Thing> inventory = FXCollections.observableArrayList();

    private ObservableList<Thing> roomContents = FXCollections.observableArrayList();

    private ObservableList<Npc> roomNpcs = FXCollections.observableArrayList();

    private NpcImageView npcImageView = new NpcImageView();

    private Map<String, Point2D> roomCoordinates = new HashMap<>();

    private Image aibaImage = new Image("file:///C:/Users/Daniel/Pictures/Danterra_Pictures/Aiba.jpg");

    /**
     * Initializes the controller and sets up the initial game state.
     * Called when the FXML file is loaded.
     */
    @FXML
    private void initialize() {
        textAreaOutput.appendText(hra.vratUvitani() + "\n");
        textAreaOutput.appendText(hra.poem() + "\n");
        textAreaOutput.appendText(hra.intro() + "\n");
        Platform.runLater(() -> playerInput.requestFocus());
        exitPanel.setItems(exitList);
        inventoryPanel.setItems(inventory);
        roomPanel.setItems(roomContents);
        npcPanel.setItems(roomNpcs);
        setPlayerStartingLocation();
        registerStavProstoruObserver();
        hra.getHerniPlan().registruj(ZmenaHry.ZMENA_MISTNOSTI, () -> {
            updateExitPanel();
            updatePlayerLocation();
            updateNpcList();
            updateRiddle();
            updateRoomContents();
            updateMinimap();
            makeSailButtonVisible();
            registerStavProstoruObserver();
            makeFireIVButtonVisible();
        });
        hra.getInventory().registruj(ZmenaHry.STAV_INVENTARE, () -> {
            updateInventory();
            makeSailButtonVisible();
            makeFireIVButtonVisible();
        });
        hra.registruj(ZmenaHry.KONEC_HRY, () -> updateGameEnding());
        resetMinimap();
        setRoomCoordinates();
        exitPanel.setCellFactory(param -> new ListCellProstor());
        inventoryPanel.setCellFactory(param -> new ListCellThing());
        roomPanel.setCellFactory(param -> new ListCellThing());
        npcPanel.setCellFactory(param -> new ListCellNpc());
        showInvisibleButton();
        makeFireIVButtonVisible();
        npcLabel.setVisible(false);
    }

    /**
     * Sets coordinates for each room in the minimap
     */
    private void setRoomCoordinates() {
        roomCoordinates.put("hall", new Point2D(125, 145));
        roomCoordinates.put("cellar", new Point2D(127, 59));
        roomCoordinates.put("tower", new Point2D(23, 145));
        roomCoordinates.put("treasure_room", new Point2D(14, 64));
        roomCoordinates.put("gate", new Point2D(229, 134));
        roomCoordinates.put("forest", new Point2D(231, 217));
        roomCoordinates.put("cliffs", new Point2D(144, 224));
        roomCoordinates.put("village", new Point2D(230, 58));
        roomCoordinates.put("pub", new Point2D(300, 56));
        roomCoordinates.put("mare_lamentorum", new Point2D(250, 151));
        roomCoordinates.put("ruins", new Point2D(135, 151));
        roomCoordinates.put("monaxia", new Point2D(15, 145));
        roomCoordinates.put("babel", new Point2D(135, 54));
    }

    /**
     * Sets starting coordinates for the player on the minimap
     */
    private void setPlayerStartingLocation() {
        player.setLayoutX(127);
        player.setLayoutY(59);
    }

    /**
     * Updates list of exits based on current game state
     * Called every time player changes (scanned) rooms
     */
    @FXML
    private void updateExitList() {
        exitList.clear();
        exitList.addAll(hra.getHerniPlan().getAktualniProstor().getVychody());
    }

    /**
     * Updates inventory based on current game state
     * called every time item is picked up or dropped
     */
    @FXML
    private void updateInventory() {
        inventory.clear();
        inventory.addAll(hra.getInventory().getItemsList());
    }

    /**
     * Updates room contents - items available in the room
     * called every time player location changes or and item
     * gets picked up or is dropped
     */
    @FXML
    private void updateRoomContents() {
        roomContents.clear();
        if (hra.getHerniPlan().getAktualniProstor().isWasScanned()) {
            roomContents.addAll(hra.getHerniPlan().getAktualniProstor().getThings());
        }
    }

    /**
     * Updates Npc list based on the current game state
     * called every time player changes room or npc
     * was talked to
     */
    @FXML
    private void updateNpcList() {
        roomNpcs.clear();
        if (hra.getHerniPlan().getAktualniProstor().isWasScanned()) {
            roomNpcs.addAll(hra.getHerniPlan().getAktualniProstor().getNpcs());
        }
    }

    /**
     * Updates riddle listView based on the current game state
     * called every time riddle is solved or player changes the room
     */
    @FXML
    private void updateRiddle() {
        answerOutput.clear();
        if (hra.getHerniPlan().getAktualniProstor().isWasScanned()) {
            if (hra.getHerniPlan().getAktualniProstor().getRiddle() != null) {
                answerOutput.setText(hra.getHerniPlan().getAktualniProstor().getRiddle().getRiddle());
            } else {
                answerOutput.setText("There's no riddle in this room");
            }
        }
    }

    /**
     * Updates player location every time player changes location
     */
    private void updatePlayerLocation() {
        String prostor = hra.getHerniPlan().getAktualniProstor().getNazev();
        player.setLayoutX(roomCoordinates.get(prostor).getX());
        player.setLayoutY(roomCoordinates.get(prostor).getY());
    }

    /**
     * When player reaches Mare Lamentorum, changes the minimap for the second picture
     */
    @FXML
    private void updateMinimap() {
        if (hra.getHerniPlan().getAktualniProstor().getNazev().equals("mare_lamentorum")) {
            minimap.setImage(new Image(getClass().getResource("HerniPlan/DanterraMap2.jpg").toExternalForm()));
        }
    }

    /**
     * Resets minimap back to first picture. Called at the start of a new game
     */
    @FXML
    private void resetMinimap(){
        minimap.setImage(new Image(getClass().getResource("HerniPlan/DanterraMap1.jpg").toExternalForm()));
    }

    /**
     * Reads player's input. Clears the input field
     *
     * @param event - Player presses enter or clicks enter button
     */
    @FXML
    private void onEnterButtonClick(ActionEvent event) {
        String command = playerInput.getText();
        playerInput.clear();
        processCommand(command);
        exitPanel.refresh();
        inventoryPanel.refresh();
        roomPanel.refresh();
    }

    /**
     * Handles answer input from the player
     *
     * @param event - Click on answer button
     */
    @FXML
    private void onAnswerButtonClick(ActionEvent event) {
        String playerAnswer = "answer " + answerInput.getText();
        answerInput.clear();
        processCommand(playerAnswer);
    }

    /**
     * Handles player calling Aiba and room scans
     *
     * @param event - click on Aiba button
     */
    @FXML
    private void onAibaButtonClick(ActionEvent event) {
        if (!hra.getAiba().isSummoned()) {
            String command = PrikazCallAiba.NAZEV;
            processCommand(command);
            npcLabel.setVisible(true);
            aibaButton.setText("aibaScan");
        } else {
            String command = PrikazAibaScan.NAZEV;
            processCommand(command);
        }
    }

    /**
     * Clears character dialogue window
     *
     * @param event - click on "OK" button
     */
    @FXML
    private void onOkButtonClick(ActionEvent event) {
        npcDialogue.clear();
        npcImage.setImage(null);
        npcLabel.setText("");
    }

    /**
     * processes user commands and appends result to text area
     * and npc dialogue area
     *
     * @param command String - player's command that'll be executed
     */

    private void processCommand(String command) {
        textAreaOutput.appendText(">" + command + "\n");
        String result = hra.zpracujPrikaz(command);
        if (command.equals("sail") || command.equals("fireIV")) {
            npcDialogue.clear();
            textAreaOutput.appendText(result + "\n");
        } else {
            npcDialogue.clear();
            npcDialogue.appendText(result);
            if (hra.getAiba().isSummoned()) {
                npcImage.setImage(aibaImage);
                npcLabel.setText("Aiba");
            }
            handleNpcDialogue(command, result, npcImage);
            //updateGameEnding();
        }
    }

    /**
     * Handles npc dialogues and npc responds to the player
     * @param command   - player's command
     * @param result    - Result of the command
     * @param imageView - NPC image
     */

    private void handleNpcDialogue(String command, String result, ImageView imageView) {
        if (command.startsWith("talkTo")) {
            String npcName = command.substring("talkTo ".length());
            Image npcImage = npcImageView.getImage(npcName);
            imageView.setFitWidth(140);
            imageView.setPreserveRatio(false);

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

    /**
     * Sets a game ending graphics when the game is over
     */
    private void updateGameEnding() {
        if (hra.konecHry()) {
            textAreaOutput.appendText(hra.ending());
            textAreaOutput.appendText(hra.playerEnding());
            textAreaOutput.appendText(hra.vratEpilog());
            playerInput.setDisable(true);
            enterButton.setDisable(true);
            aibaButton.setDisable(true);
            exitPanel.setDisable(true);
            tabPane.setDisable(true);
            inventoryPanel.setDisable(true);
            okButton.setDisable(true);

        }
    }

    /**
     * Terminates game
     *
     * @param event - player confirms they're sure to quit the game
     */
    @FXML
    private void terminateGame(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to quit the game?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    /**
     * Starts a new game
     * resets everything into initial state of the game
     * @param event - player clicks on new game button
     */
    @FXML
    private void newGame(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to restart the game?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            clearEverything();
            hra = new Hra();
            initialize();
            aibaButton.setText("Call Aiba");
            playerInput.setDisable(false);
            enterButton.setDisable(false);
            exitPanel.setDisable(false);
            tabPane.setDisable(false);
            aibaButton.setDisable(false);
            inventoryPanel.setDisable(false);
            okButton.setDisable(false);
        }
    }

    /**
     * Clears every piece of UI
     * Called when the game restarts
     */
    @FXML
    private void clearEverything() {
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

    /**
     * Opens help for the player in a webView
     *
     * @param event player's click on help button
     */
    @FXML
    private void helpClick(ActionEvent event) {
        Stage helpStage = new Stage();
        WebView webView = new WebView();
        Scene helpScene = new Scene(webView);
        webView.getEngine().load(getClass().getResource("help.html").toExternalForm());
        helpStage.setScene(helpScene);
        helpStage.show();
    }

    /**
     * Updates contents of exit panel in case the current room was scanned
     */
    @FXML
    private void updateExitPanel() {
        if (hra.getHerniPlan().getAktualniProstor().isWasScanned()) {
            updateExitList();
        } else {
            exitList.clear();
        }
    }

    /**
     * Makes travelling between rooms possible on mouse click
     * Checks if destination is locked - if so, executes unlock command
     * otherwise "jdi" command is executed
     * @param mouseEvent click on Exit panel
     */
    @FXML
    private void clickExitPanel(MouseEvent mouseEvent) {
        Prostor destination = exitPanel.getSelectionModel().getSelectedItem();
        if (destination == null) return;
        if (destination.isLocked()) {
            String destinationString = destination.toString().replace("(Locked)", "");
            String command = PrikazUnlock.NAZEV + " " + destinationString;
            processCommand(command);
            updateExitPanel();
            return;
        }
        String command = PrikazJdi.NAZEV + " " + destination;
        processCommand(command);
        //updateExitPanel();
        //exitPanel.refresh();
        //roomPanel.refresh();

    }

    /**
     * Allows players to pickup room contents by clicking on them
     * Pickup command is executed after click
     * @param mouseEvent click on Thing list cell in roomContents
     */
    @FXML
    private void clickRoomContents(MouseEvent mouseEvent) {
        Thing targetItem = roomPanel.getSelectionModel().getSelectedItem();
        if (targetItem == null) return;
        String command = PrikazPickup.NAZEV + " " + targetItem;
        processCommand(command);
        roomPanel.refresh();
    }

//    @FXML
//    private void clickNpcPanel(MouseEvent mouseEvent){
//        Npc targetNpc = npcPanel.getSelectionModel().getSelectedItem();
//        if (targetNpc == null) return;
//        String command = PrikazTalkTo.NAZEV + " " + targetNpc.getName();
//        processCommand(command);
//        updateNpcList();
//        npcPanel.refresh();
//    }

    /**
     * Gives players opportunity to interact with items in inventory
     * Shows pop up that gives an option to drop or use an item
     * Depending on player's choice executes drop or use command
     * @param mouseEvent click in item in the inventory
     */
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
            //updateInventory();
            //updateRoomContents();
            inventoryPanel.refresh();
            roomPanel.refresh();
        }
    }
    /**
     * Allows players to interact NPCs by clicking on npc panel
     * Shows pop up that gives an option to talkTo npc or to giveItem to npc
     * if talkTo is chosen - executes talkTo command
     * if GiveItem is chosen dialog window asking for item name is shown
     * @param mouseEvent click on npcListCell
     */
    @FXML
    private void clickNpcPanel(MouseEvent mouseEvent) {
        Npc targetNpc = npcPanel.getSelectionModel().getSelectedItem();
        if (targetNpc == null) return;

        ChoiceDialog<String> dialog = new ChoiceDialog<>("TalkTo", "GiveItem");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("TalkTo or GiveItem");
        dialog.setHeaderText("What would you like to do?");
        dialog.setContentText("Choose an option:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String choice = result.get();
            String command;

            if ("TalkTo".equals(choice)) {
                command = PrikazTalkTo.NAZEV + " " + targetNpc.getName();
            } else if ("GiveItem".equals(choice)) {
                TextInputDialog itemDialog = new TextInputDialog();
                itemDialog.setTitle("Give Item");
                itemDialog.setHeaderText("Enter the name of the item you want to give:");
                itemDialog.setContentText("Item Name:");

                Optional<String> itemResult = itemDialog.showAndWait();
                if (itemResult.isPresent() && !itemResult.get().trim().isEmpty()) {
                    String itemName = itemResult.get().trim();
                    command = PrikazGiveItem.NAZEV + " " + itemName + " " + targetNpc.getName();
                } else {
                    return;
                }
            } else {
                return;
            }

            processCommand(command);
            updateNpcList();
            //updateInventory();
        }
    }


    /**
     * Registers an observer for changes in the game state within the current location.
     * This method registers an observer to monitor changes in the game state, specifically the state of the current
     * location (prostor). When the game state changes, this observer is triggered, and it updates various elements in
     * the user interface to reflect those changes. The elements updated include the exit panel, room contents, riddles,
     * and the list of non-playable characters (NPCs).
     */
    private void registerStavProstoruObserver() {
        hra.getHerniPlan().getAktualniProstor().registruj(ZmenaHry.STAV_PROSTORU, () -> {
            updateExitPanel();
            updateRoomContents();
            updateRiddle();
            updateNpcList();
        });
    }

    /**
     * Executes sail command when sail button is clicked on, making button invisible after
     */
    @FXML
    private void onSailButtonClick() {
        String command = PrikazSail.NAZEV;
        processCommand(command);
        setButtonVisibility(false);
    }
    /**
     * Executes fireIV command when sail button is clicked on, making button invisible after
     */
    @FXML
    private void onFireIVButtonClick(){
        String command = PrikazFireIV.NAZEV;
        processCommand(command);
        setButtonVisibility(false);
    }
    /**
     * Sail button is visible only if certain conditions are met
     */
    public void makeSailButtonVisible() {
        if (hra.getHerniPlan().getAktualniProstor().getNazev().equals("pub") && hra.getInventory().containsItem("ticket")) {
            setButtonVisibility(true);
            showInvisibleButton();
        }else {
            setButtonVisibility(false);
            showInvisibleButton();
        }
    }
    /**
     * FireIV button is visible only if certain conditions are met
     */
    public void makeFireIVButtonVisible() {
        if (hra.getHerniPlan().getAktualniProstor().getNazev().equals("monaxia") && hra.getInventory().containsItem("magicRod_u")) {
            fireIVButton.setVisible(true);
            showInvisibleButton();
        } else {
            fireIVButton.setVisible(false);
            showInvisibleButton();
        }
    }

    /**
     * Setter for button visibility
     */
    public void setButtonVisibility(boolean isVisible) {
        isButtonVisible.set(isVisible);
    }
    /**
     * Shows an invisible button based on a conditional property.
     * This method binds the visibility of the "sailButton" to a condition defined by "isButtonVisible".
     * If the condition is met (true), the button is set to visible, otherwise, it's set to invisible.
     */
    private void showInvisibleButton() {
        sailButton.visibleProperty().bind(Bindings.when(isButtonVisible).then(true).otherwise(false));
    }
}