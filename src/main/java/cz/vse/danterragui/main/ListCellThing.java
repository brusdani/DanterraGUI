package cz.vse.danterragui.main;

import cz.vse.danterragui.logika.Prostor;
import cz.vse.danterragui.logika.Thing;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * Class ListCellThing - Defines how should Thing listCell look like
 * in a ListView. Also dealing with updating cells
 * @author Daniel Brus
 * @version 1.0 , October 2023
 */

public class ListCellThing extends ListCell<Thing> {
    public ListCellThing() {
        setThingImages(); // Call this method to populate the map in the constructor
    }
    private Map<String, Image> thingImages = new HashMap<>();
    /**
     * Matches each "Thing" from the game with its personal image
     */
    private void setThingImages(){
        thingImages.put("lantern", new Image(getClass().getResource("Things/Lantern.png").toExternalForm()));
        thingImages.put("chain", new Image(getClass().getResource("Things/Chain.jpg").toExternalForm()));
        thingImages.put("treasureRoom_key", new Image(getClass().getResource("Things/Key.jpg").toExternalForm()));
        thingImages.put("gate_key", new Image(getClass().getResource("Things/Key.jpg").toExternalForm()));
        thingImages.put("chakrams", new Image(getClass().getResource("Things/Chakram.png").toExternalForm()));
        thingImages.put("necklace", new Image(getClass().getResource("Things/Necklace.jpg").toExternalForm()));
        thingImages.put("ring", new Image(getClass().getResource("Things/Ring.png").toExternalForm()));
        thingImages.put("note_u", new Image(getClass().getResource("Things/Note.png").toExternalForm()));
        thingImages.put("sword", new Image(getClass().getResource("Things/Sword.png").toExternalForm()));
        thingImages.put("advice_u", new Image(getClass().getResource("Things/Advice.jpg").toExternalForm()));
        thingImages.put("moonstone", new Image(getClass().getResource("Things/Moon.jpg").toExternalForm()));
        thingImages.put("sunstone", new Image(getClass().getResource("Things/Sun.png").toExternalForm()));
        thingImages.put("ticket", new Image(getClass().getResource("Things/Ticket.png").toExternalForm()));

    }
    /**
     * Updates list cell in ListView
     * @param item - Thing class within a cell
     * @param empty - passes information whether cell is empty
     */
    @Override
    protected void updateItem(Thing item, boolean empty) {
        super.updateItem(item, empty);
        if(empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.toString());
            ImageView imageView = new ImageView(thingImages.get(item.getName()));
            imageView.setFitWidth(60);
            imageView.setPreserveRatio(true);
            setGraphic(imageView);
        }

    }
}
