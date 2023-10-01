package cz.vse.danterragui.main;

import cz.vse.danterragui.logika.Prostor;
import cz.vse.danterragui.logika.Thing;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class ListCellThing extends ListCell<Thing> {
    public ListCellThing() {
        setThingImages(); // Call this method to populate the map in the constructor
    }
    private Map<String, Image> thingImages = new HashMap<>();

    private void setThingImages(){
        thingImages.put("lantern", new Image(getClass().getResource("Things/Lantern.png").toExternalForm()));
        thingImages.put("chain", new Image(getClass().getResource("Things/Lantern.png").toExternalForm()));
    }

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
