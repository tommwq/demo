package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicAccountProxy implements InvocationHandler {

    private Object target;

    public static Object create(Object target) {
        DynamicAccountProxy p = new DynamicAccountProxy();
        p.target = target;
        Class clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(),
                                      clazz.getInterfaces(), p);
    }

    private DynamicAccountProxy() {}

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

        System.out.println("DynamicProxy invoke " + method.toString() + argumentString + " returns " + resultString);
        return result;
    }
}

