package event.handler;

import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import entity.Entity;

/**
 * Created by Vearthtek on 2017-06-14.
 */
public class DoubleEventHandler implements EventHandler<TableColumn.CellEditEvent<Entity, Double>> {
    @Override
    public void handle(TableColumn.CellEditEvent<Entity, Double> t) {
        if (t.getNewValue().isNaN()) {
            ((Entity) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
            ).setSalary(t.getOldValue());
            t.getTableColumn().setVisible(false);
            t.getTableColumn().setVisible(true);
        } else {
            ((Entity) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())
            ).setSalary(t.getNewValue());
        }
    }
}
