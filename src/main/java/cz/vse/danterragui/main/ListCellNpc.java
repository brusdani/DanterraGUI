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
        npcImages.put("ash", new Image(getClass().getResource("Npcs/Ash.jpg").toExternalForm()));
        npcImages.put("guard", new Image(getClass().getResource("Npcs/Guard.jpg").toExternalForm()));
        npcImages.put("keeper", new Image(getClass().getResource("Npcs/Keeper.jpg").toExternalForm()));
        npcImages.put("ravi", new Image(getClass().getResource("Npcs/Thief.jpg").toExternalForm()));
        npcImages.put("roy", new Image(getClass().getResource("Npcs/Thief.jpg").toExternalForm()));
        npcImages.put("kira", new Image(getClass().getResource("Npcs/Kira.jpg").toExternalForm()));
        npcImages.put("nemo", new Image(getClass().getResource("Npcs/Nemo.jpg").toExternalForm()));
        npcImages.put("monk", new Image(getClass().getResource("Npcs/Monk.jpg").toExternalForm()));
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
