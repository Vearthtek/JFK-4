package event.handler;

import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import entity.Entity;

/**
 * Created by Vearthtek on 2017-06-14.
 */
public class StringEventHandler implements EventHandler<TableColumn.CellEditEvent<Entity, String>> {
    @Override
    public void handle(TableColumn.CellEditEvent<Entity, String> t) {
        ((Entity) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
        ).setFirstName(t.getNewValue());
    }
}
