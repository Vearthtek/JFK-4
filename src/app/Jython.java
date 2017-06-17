package app;

import entity.Entity;
import javafx.collections.ObservableList;
import org.python.core.PyList;
import org.python.util.PythonInterpreter;

import javax.script.*;
import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Created by Vearthtek on 2017-06-15.
 */
public class Jython {

    private PythonInterpreter pythonInterpreter;
    private PyList retEntities;

    public String execute(String code, ObservableList<Entity> entities) throws ScriptException {
        pythonInterpreter = new PythonInterpreter();

        PyList list = new PyList(entities);

        StringWriter writerOut = new StringWriter();
        StringWriter writerErr = new StringWriter(); //nie za bardzo zbiera errorStream
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptContext context = new SimpleScriptContext();
        context.setAttribute("entities", list, ScriptContext.ENGINE_SCOPE);

        context.setWriter(writerOut);
        context.setErrorWriter(writerErr);
        ScriptEngine engine = manager.getEngineByName("python");
        Object scriptRetValue;
        try {
            scriptRetValue = engine.eval(code, context);
        } catch (ScriptException e) {
            throw e;
        }
        retEntities = (PyList) context.getAttribute("entities");
        /*PyList l = (PyList) context.getAttribute("entities");*/
        return scriptRetValue != null ? scriptRetValue.toString() : writerOut.toString();
    }

    public ArrayList<Entity> getRetEntities() {
        ArrayList<Entity> list = new ArrayList<>(retEntities);
        return list;
    }

}
