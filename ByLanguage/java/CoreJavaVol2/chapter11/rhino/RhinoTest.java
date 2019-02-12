import javax.script.*;

public class RhinoTest {
    public static void main(String... args) throws Exception {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");

        engine.eval("n = 1");
        Object result = engine.eval("n + 1");
        System.out.println(result);
    }
}
