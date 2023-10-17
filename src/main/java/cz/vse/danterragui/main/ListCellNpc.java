package cz.vse.danterragui.main;

import cz.vse.danterragui.logika.Npc;
import cz.vse.danterragui.logika.Thing;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * Class ListCellNpc - Defines how should npc's listCell look like
 * in a ListView. Also dealing with updating cells
 * @author Daniel Brus
 * @version 1.0 , October 2023
 */
public class ListCellNpc extends ListCell<Npc> {

    /**
     * Constructor
     * Creates ListCellNpc
     */
    public ListCellNpc(){
        setNpcImages();
    }
    private Map<String, Image> npcImages = new HashMap<>();
    /**
     * Matches each npc from the game with its personal image
     */
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

    /**
     * Updates list cell in ListView
     * @param npc - Npc class within a cell
     * @param empty - passes information whether cell is empty
     */
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
