package cz.vse.danterragui.main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class NpcImageView extends ImageView {

    public NpcImageView() {
        setNpcImages();
    }
    private Map<String, Image> npcImages = new HashMap<>();

    private void setNpcImages(){
        npcImages.put("aiba", new Image(getClass().getResource("Npcs/Aiba.jpg").toExternalForm()));
        npcImages.put("ghost", new Image(getClass().getResource("Npcs/Ghost.jpg").toExternalForm()));
    }

    public Image getImage(String npcName){
        return npcImages.get(npcName);
    }

}
