package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class GenericDynamicProxy implements InvocationHandler {

    private Object target;

    public static <T> T create(T target) {
        GenericDynamicProxy p = new GenericDynamicProxy();
        p.target = target;
        Class clazz = target.getClass();
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                                          clazz.getInterfaces(),
                                          p);
    }

    private GenericDynamicProxy() {}

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String argumentString = "()";

        StringBuilder sb = new StringBuilder();
        if (args != null) {
            sb.append("(");
            boolean first = true;
            for (Object a: args) {
                if (!first) {
                    sb.append(",");
                }
                sb.append(a.toString());
                first = false;
            }
            sb.append(")");
            argumentString = sb.toString();
        }
        
        Object result = null;
        result = method.invoke(target, args);

        String resultString = "null";
        if (result != null) {
            resultString = result.toString();
        }

        System.out.println("GenericDynamicProxy invoke " + method.toString() + argumentString + " returns " + resultString);
        return result;
    }
}

