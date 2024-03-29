package cz.vse.danterragui.main;

import cz.vse.danterragui.logika.Prostor;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * Class ListCellProstor - Defines how should Prostor listCell look like
 * in a ListView. Also dealing with updating cells
 * @author Daniel Brus
 * @version 1.0 , October 2023
 */
public class ListCellProstor extends ListCell<Prostor> {
    /**
     * Constructor
     */
    public ListCellProstor() {
        setRoomImages(); // Call this method to populate the map in the constructor
    }

    private Map<String, Image> roomImages = new HashMap<>();
    /**
     * Matches each "Prostor"(room) from the game with its personal image
     */
    private void setRoomImages(){
        roomImages.put("cellar",new Image(getClass().getResource("Prostory/cellar.jpg").toExternalForm()));
        roomImages.put("hall",new Image(getClass().getResource("Prostory/Hall.jpg").toExternalForm()));
        roomImages.put("tower",new Image(getClass().getResource("Prostory/Tower.jpg").toExternalForm()));
        roomImages.put("lock",new Image(getClass().getResource("Prostory/lock.jpg").toExternalForm()));
        roomImages.put("treasure_room",new Image(getClass().getResource("Prostory/TreasureRoom.jpg").toExternalForm()));
        roomImages.put("gate",new Image(getClass().getResource("Prostory/Gate.jpg").toExternalForm()));
        roomImages.put("forest",new Image(getClass().getResource("Prostory/Forest.jpg").toExternalForm()));
        roomImages.put("cliffs",new Image(getClass().getResource("Prostory/Cliffs.jpg").toExternalForm()));
        roomImages.put("village",new Image(getClass().getResource("Prostory/Village.jpg").toExternalForm()));
        roomImages.put("pub",new Image(getClass().getResource("Prostory/Pub.jpg").toExternalForm()));
        roomImages.put("mare_lamentorum",new Image(getClass().getResource("Prostory/MareLamentorum.png").toExternalForm()));
        roomImages.put("ruins",new Image(getClass().getResource("Prostory/Ruins.jpg").toExternalForm()));
        roomImages.put("monaxia",new Image(getClass().getResource("Prostory/Monaxia.jpg").toExternalForm()));
        roomImages.put("babel",new Image(getClass().getResource("Prostory/Babel.jpg").toExternalForm()));
    }
    /**
     * Updates list cell in ListView
     * @param prostor - Prostor class within a cell
     * @param empty - passes information whether cell is empty
     */
    @Override
    protected void updateItem(Prostor prostor, boolean empty) {
        super.updateItem(prostor, empty);
        if(empty){
            setText(null);
            setGraphic(null);
        } else if (prostor.toString().endsWith("(Locked)")) {
            setText(prostor.toString());
            ImageView imageView = new ImageView(roomImages.get("lock"));
            imageView.setFitWidth(120);
            imageView.setPreserveRatio(true);
            setGraphic(imageView);
        } else {
            setText(prostor.toString());
            ImageView imageView = new ImageView(roomImages.get(prostor.getNazev()));
            imageView.setFitWidth(120);
            imageView.setPreserveRatio(true);
            setGraphic(imageView);
        }
    }
}
