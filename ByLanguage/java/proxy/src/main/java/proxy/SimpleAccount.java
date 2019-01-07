package proxy;

public class SimpleAccount implements Account {
    private double balance = 0.0;
    
    public double getBalance(){
        return balance;
    }

    public void save(double amount){
        balance += amount;
    }

    public void withdraw(double amount){
        balance -= amount;
    }
}

