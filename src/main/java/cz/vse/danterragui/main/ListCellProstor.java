package cz.vse.danterragui.main;

import cz.vse.danterragui.logika.Prostor;
import javafx.scene.control.ListCell;

public class ListCellProstor extends ListCell<Prostor> {
    @Override
    protected void updateItem(Prostor prostor, boolean empty) {
        super.updateItem(prostor, empty);
        setText(prostor.toString());
    }
}
