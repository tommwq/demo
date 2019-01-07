package proxy;

public interface Account {
    double getBalance();
    void save(double amount);
    void withdraw(double amount);
}

