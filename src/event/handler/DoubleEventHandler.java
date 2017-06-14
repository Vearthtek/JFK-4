package event.handler;

import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Vearthtek on 2017-06-14.
 */
public class DoubleEventHandler implements EventHandler<TableColumn.CellEditEvent<Entity, Double>> {
    private Method method;

    public DoubleEventHandler(Method method) {
        this.method = method;
    }

    @Override
    public void handle(TableColumn.CellEditEvent<Entity, Double> t) {
        Entity e = t.getTableView().getItems().get(t.getTablePosition().getRow());
        try {if (t.getNewValue().isNaN()) {
            method.invoke(e, t.getOldValue());
            t.getTableColumn().setVisible(false);
            t.getTableColumn().setVisible(true);
        } else {
            method.invoke(e, t.getNewValue());
        }
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            e1.printStackTrace();
        }

    }
}
