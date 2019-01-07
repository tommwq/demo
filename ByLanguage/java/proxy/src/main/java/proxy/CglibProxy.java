package proxy;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxy implements MethodInterceptor {

    private Object target;
    
    public static Object create(Object target) {
        CglibProxy p = new CglibProxy(target);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(p.target.getClass());
        enhancer.setCallback(p);
        return enhancer.create();
    }

    private CglibProxy(Object target) {
        this.target = target;
    }

    public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("CglibProxy invoke " + method.toString());
        return proxy.invokeSuper(object, args);
    }
}
