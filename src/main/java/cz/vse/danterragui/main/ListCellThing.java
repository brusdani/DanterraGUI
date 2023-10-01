package cz.vse.danterragui.main;

import cz.vse.danterragui.logika.Thing;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ListCellThing extends ListCell<Thing> {

    private Map<String, Image> thingImages = new HashMap<>();

    private void setThingImages(){
        thingImages.put("lantern", new Image(getClass().getResource("Things/cellar.jpg").toExternalForm()));
    }
}
