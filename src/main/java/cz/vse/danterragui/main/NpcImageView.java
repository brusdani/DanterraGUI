package cz.vse.danterragui.main;

import cz.vse.danterragui.logika.Npc;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;
/**
 * Class NpcImageView - Defines how should npc's ImageView looks like
 * Npc image view is important for npc dialogue
 * @author Daniel Brus
 * @version 1.0 , October 2023
 */
public class NpcImageView extends ImageView {
    /**
     * Constructor
     */
    public NpcImageView() {
        setNpcImages();
    }
    private Map<String, Image> npcImages = new HashMap<>();
    /**
     * Matches each "Npc from the game with its personal image
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

    public Image getImage(String npcName){
        return npcImages.get(npcName);
    }

}
