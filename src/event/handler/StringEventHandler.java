package event.handler;

import entity.Entity;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Vearthtek on 2017-06-14.
 */
public class StringEventHandler implements EventHandler<TableColumn.CellEditEvent<Entity, String>> {

    private Method method;

    public StringEventHandler(Method method) {
        this.method = method;
    }

    @Override
    public void handle(TableColumn.CellEditEvent<Entity, String> t) {
        Entity e = t.getTableView().getItems().get(t.getTablePosition().getRow());
        try {
            method.invoke(e, t.getNewValue());
        } catch (IllegalAccessException e1) {
            System.out.println("kurwa1");
            e1.printStackTrace();
        } catch (InvocationTargetException e1) {
            System.out.println("kurwa2");
            e1.printStackTrace();
        }
    }
}
