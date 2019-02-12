import java.lang.reflect.*;
import java.util.ArrayList;

public class ObjectAnalyzer {
    private ArrayList<Object> visited = new ArrayList<>();

    public String toString(Object object) {
        if (object == null) {
            return "null";
        }

        if (visited.contains(object)) {
            return "...";
        }
        visited.add(object);
        Class clazz = object.getClass();
        if (clazz == String.class) return (String) object;
        if (clazz.isArray()) {
            String r = clazz.getComponentType() + "[]{";
            for (int i = 0; i < Array.getLength(object); i++) {
                if (i > 0) r += ",";
                Object val = Array.get(object, i);
                if (clazz.getComponentType().isPrimitive()) r += val;
                else
                    r += toString(val);
            }
            return r + "}";
        }

        String r = clazz.getName();
        do {
            r += "[";
            Field[] fields = clazz.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            for (Field field: fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }

                if (!r.endsWith("[")) r += ",";
                r += field.getName() + "=";
                try {
                    Class type = field.getType();
                    Object val = field.get(object);
                    if (type.isPrimitive()) r += val;
                    else r += toString(val);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            r += "]";
            clazz = clazz.getSuperclass();
        } while (clazz != null);
        return r;
    }
}
