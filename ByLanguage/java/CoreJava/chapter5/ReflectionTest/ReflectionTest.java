import java.util.*;
import java.lang.reflect.*;

public class ReflectionTest {
    public static void main(String[] args) {
        String name;

        if (args.length > 0) {
            name = args[0];
        } else {
            Scanner in = new Scanner(System.in);
            System.out.println("Enter class name (e.g. java.util.Date): ");
            name = in.next();
        }

        try {
            Class clazz = Class.forName(name);
            String modifiers = Modifier.toString(clazz.getModifiers());
            if (!modifiers.isEmpty()) {
                System.out.print(modifiers + " ");
            }
            
            System.out.println("class " + name);

            Class superClass = clazz.getSuperclass();
            if (superClass !=  null && superClass != Object.class) {
                System.out.print(" extends " + superClass.getName());
            }

            System.out.println();
            System.out.println("{");
            System.out.println();
            printConstructors(clazz);
            System.out.println();
            printMethods(clazz);
            System.out.println();
            printFields(clazz);
            System.out.println("}");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    public static void printConstructors(Class clazz) {
        for (Constructor ctor: clazz.getDeclaredConstructors()) {
            System.out.print("    ");
            String modifiers = Modifier.toString(ctor.getModifiers());
            if (!modifiers.isEmpty()) {
                System.out.print(modifiers + " ");
            }
            
            String name = ctor.getName();
            System.out.print(name + "(");

            boolean isFirst = true;
            for (Class type: ctor.getParameterTypes()) {
                if (!isFirst) {
                    System.out.print(", ");
                }
                System.out.println(type.getName());
                isFirst = false;
            }
            System.out.println(");");
        }
    }

    public static void printMethods(Class clazz) {
        for (Method method: clazz.getDeclaredMethods()) {
            Class returnType = method.getReturnType();
            String name = method.getName();

            System.out.print("    ");
            String modifiers = Modifier.toString(method.getModifiers());
            if (!modifiers.isEmpty()) {
                System.out.print(modifiers + " ");
            }
            System.out.print(returnType.getName() + " " + name + "(");

            boolean isFirst = true;
            for (Class type: method.getParameterTypes()) {
                if (!isFirst) {
                    System.out.print(", ");
                }
                System.out.print(type.getName());
                isFirst = false;
            }
            System.out.println(");");
        }
    }

    public static void printFields(Class clazz) {
        for (Field field: clazz.getDeclaredFields()) {
            Class type = field.getType();
            String name = field.getName();
            System.out.print("    ");
            String modifiers = Modifier.toString(field.getModifiers());
            if (!modifiers.isEmpty()) {
                System.out.print(modifiers + " ");
            }

            System.out.println(type.getName() + " " + name + ";");
        }
    }
}
