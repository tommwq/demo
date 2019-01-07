package proxy;

public class App {

    public void testStaticProxy() {
        Account account = new StaticAccountProxy(new SimpleAccount());
        account.save(1010);
        account.getBalance();
    }

    public void testDynamicProxy() {
        Account account = (Account) DynamicAccountProxy.create(new SimpleAccount());
        account.save(1010);
        account.getBalance();
    }

    public void testGenericDynamicProxy() {
        Account account = GenericDynamicProxy.<Account>create(new SimpleAccount());
        account.save(1010);
        account.getBalance();
    }

    public void testCglibProxy() {
        Account account = (Account) CglibProxy.create(new SimpleAccount());
        account.save(1010);
        account.getBalance();
    }

    public static void main(String[] args) {
        App app = new App();
        app.testStaticProxy();
        app.testDynamicProxy();
        app.testGenericDynamicProxy();
        app.testCglibProxy();
    }
}
