package app;

import entity.Entity;
import javafx.collections.ObservableList;
import jdk.nashorn.internal.objects.NativeJSON;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

/**
 * Created by Vearthtek on 2017-06-14.
 */
public class Rhino {
    private Context context;
    private Scriptable scope;

    public String execute(String code, ObservableList<Entity> items) {
        context = Context.enter();
        try {
            scope = context.initStandardObjects();

            Object[] array = items.toArray();
            ScriptableObject.putProperty(scope, "entities", array);

            //Number(e.get(0).getSalary())+1
            ScriptableObject so = new NativeArray(array);
            Object wrappedOut = Context.javaToJS(so, scope);
            ScriptableObject.putProperty(scope, "e", so);
            /*Object wrappedOut = Context.javaToJS(items, scope);
            ScriptableObject.putProperty(scope, "e", wrappedOut);*/

            Object result = context.evaluateString(scope, code, "<cmd>", 1, null);
            return context.toString(result);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ex.getMessage();
        } finally {
            Context.exit();
        }
    }
}
