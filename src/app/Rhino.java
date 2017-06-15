package app;

import entity.Entity;
import entity.EntityJS;
import javafx.collections.ObservableList;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * Created by Vearthtek on 2017-06-14.
 */
public class Rhino {
    private Context context;
    private Scriptable scope;
    private Object retEntities;

    public String execute(String code, ObservableList<Entity> entities) throws Exception {
        context = Context.enter();
        try {
            scope = context.initStandardObjects();

            ScriptableObject.defineClass(scope, EntityJS.class, true, true);

            EntityJS[] objects = new EntityJS[entities.size()];
            for (int i = 0; i < entities.size(); ++i) {
                objects[i] = new EntityJS(entities.get(i).getFirstName(), entities.get(i).getLastName(), entities.get(i).getSalary(), entities.get(i).getEmail());
            }

            ScriptableObject.putProperty(scope, "e1", Context.javaToJS(objects, scope));
            ScriptableObject.putProperty(scope, "e4", Context.javaToJS(objects[0], scope));
            ScriptableObject.putProperty(scope, "e5", objects[0]);

            Object[] array = entities.toArray();
            ScriptableObject.putProperty(scope, "e2", array);

            //Number(e.get(0).getSalary())+1
            Object wrappedOut = Context.javaToJS(entities, scope);
            ScriptableObject.putProperty(scope, "e3", wrappedOut);

            Object result = context.evaluateString(scope, code, "<cmd>", 1, null);
            retEntities = ((NativeJavaObject) scope.get("e3", scope)).unwrap();//TODO: change it to 'entities'
            return context.toString(result);
        } catch (Exception ex) {
            throw new Exception(ex);
        } finally {
            Context.exit();
        }
    }

    public Object getRetEntities() {
        return retEntities;
    }
}
