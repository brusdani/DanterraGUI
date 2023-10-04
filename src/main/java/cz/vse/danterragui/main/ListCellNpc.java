package cz.vse.danterragui.main;

import cz.vse.danterragui.logika.Npc;
import cz.vse.danterragui.logika.Thing;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class ListCellNpc extends ListCell<Npc> {

    public ListCellNpc(){
        setNpcImages();
    }
    private Map<String, Image> npcImages = new HashMap<>();

    private void setNpcImages(){
        npcImages.put("aiba", new Image(getClass().getResource("Npcs/Aiba.jpg").toExternalForm()));
        npcImages.put("ghost", new Image(getClass().getResource("Npcs/Ghost.jpg").toExternalForm()));
    }

    @Override
    protected void updateItem(Npc npc, boolean empty) {
        super.updateItem(npc, empty);
        if(empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(npc.getName());
            ImageView imageView = new ImageView(npcImages.get(npc.getName()));
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);
            setGraphic(imageView);
        }

    }
}
