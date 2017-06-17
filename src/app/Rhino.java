package app;

import entity.Entity;
import entity.EntityJS;
import javafx.collections.ObservableList;
import org.mozilla.javascript.*;

import java.util.ArrayList;

/**
 * Created by Vearthtek on 2017-06-14.
 */
public class Rhino {
    private Context context;
    private Scriptable scope;
    private NativeArray retEntities;

    private class NullCallable implements Callable {
        @Override
        public Object call(Context context, Scriptable scope, Scriptable holdable, Object[] objects) {
            return objects[1];
        }
    }

    public String execute(String code, ObservableList<Entity> entities) throws Exception {
        context = Context.enter();
        try {
            scope = context.initStandardObjects();

            ScriptableObject.defineClass(scope, EntityJS.class, true, true);

            EntityJS[] objects = new EntityJS[entities.size()];
            for (int i = 0; i < entities.size(); ++i) {
                objects[i] = new EntityJS(entities.get(i).getFirstName(), entities.get(i).getLastName(), entities.get(i).getSalary(), entities.get(i).getEmail());
            }

            //najlepiej zrzucic entities do JSONa, potem przeparsowac na obiekt JSowy i wrzucic do scope'a
            //dzieki temu entities jest prawdziwym JSowym obiektem
            NativeArray array = new NativeArray(objects);
            Object JSONString = NativeJSON.stringify(context, scope, array, null, null);
            Object JSON = NativeJSON.parse(context, scope, JSONString.toString(), new NullCallable());
            ScriptableObject.putProperty(scope, "entities", JSON);

//            Moje inne proby:
//
//            ScriptableObject.putProperty(scope, "e1", Context.javaToJS(objects, scope));
//             zwroci "[Lentity.EntityJS;@7959b389"
//            ScriptableObject.putProperty(scope, "e4", Context.javaToJS(objects[0], scope));
//             TypeError: Cannot find default value for object.
//            ScriptableObject.putProperty(scope, "e5", objects[0]);
//            TypeError: Cannot find default value for object.
//
//            Object[] array = entities.toArray();
//            ScriptableObject.putProperty(scope, "e2", array);
//            [Ljava.lang.Object;@5bd8b387
//
//            Number(e.get(0).getSalary())+1
//            Object wrappedOut = Context.javaToJS(entities, scope);
//            ScriptableObject.putProperty(scope, "e3", wrappedOut);
//            zwraca fajny array, niestety, aby dostac sie do elementow uzyc Javovskiej metody get() np:
//            Number(e.get(0).getSalary())+1
//            [Entity{firstName='Alwafaa', lastName='Abacki', salary=1000.0, email='zdzisiek@adad.com'},
//            Entity{firstName='chero', lastName='Cabacki', salary=2000.0, email='bfadaw@dadaad.com'}]

            Object result = context.evaluateString(scope, code, "<script>", 1, null);
            retEntities = (NativeArray) scope.get("entities", scope);
            return context.toString(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex);
        } finally {
            Context.exit();
        }
    }

    public ArrayList<Entity> getRetEntities() {
        ArrayList<Entity> list = new ArrayList<>(retEntities.size());
        for (int i = 0; i < retEntities.size(); ++i) {
            NativeObject o = (NativeObject) retEntities.get(i);
            list.add(new Entity((String) o.get("firstName"), (String) o.get("lastName"), Double.parseDouble(o.get("salary").toString()), (String) o.get("email")));
        }
        return list;
    }
}
