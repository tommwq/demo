package proxy;

public class StaticAccountProxy implements Account {

    private Account account;

    public StaticAccountProxy(Account account) {
        this.account = account;
    }
    
    public double getBalance(){
        double result = account.getBalance();
        System.out.println("StaticProxy.getBalance returns " + result);
        return result;
    }

    public void save(double amount){
        account.save(amount);
        System.out.println("StaticProxy.save  " + amount);
    }

    public void withdraw(double amount){     
        account.withdraw(amount);
        System.out.println("StaticProxy.withdraw  " + amount);
    }
}

