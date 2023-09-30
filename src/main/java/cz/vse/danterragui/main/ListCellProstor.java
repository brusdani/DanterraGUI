package cz.vse.danterragui.main;

import cz.vse.danterragui.logika.Prostor;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class ListCellProstor extends ListCell<Prostor> {

    public ListCellProstor() {
        setRoomImages(); // Call this method to populate the map in the constructor
    }

    private Map<String, Image> roomImages = new HashMap<>();

    private void setRoomImages(){
        roomImages.put("cellar",new Image(getClass().getResource("Prostory/cellar.jpg").toExternalForm()));
        roomImages.put("hall",new Image(getClass().getResource("Prostory/cellar.jpg").toExternalForm()));
        roomImages.put("tower",new Image(getClass().getResource("Prostory/cellar.jpg").toExternalForm()));
        roomImages.put("lock",new Image(getClass().getResource("Prostory/lock.jpg").toExternalForm()));
        roomImages.put("treasure_room",new Image(getClass().getResource("Prostory/cellar.jpg").toExternalForm()));
    }

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
