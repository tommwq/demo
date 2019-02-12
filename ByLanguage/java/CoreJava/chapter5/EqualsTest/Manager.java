public class Manager extends Employee {
    private double bonus;

    public Manager(String n, double s, int year, int month, int day) {
        super(n, s, year, month, day);
        bonus = 0;
    }

    public double getSalary() {
        double base = super.getSalary();
        return base + bonus;
    }

    public void setBonus(double b) {
        bonus =b;
    }

    public boolean equals(Object operand) {
        if (!super.equals(operand)) {
            return false;
        }

        return bonus == ((Manager) operand).bonus;
    }

    public int hashCode() {
        return super.hashCode() + 17 * new Double(bonus).hashCode();
    }

    public String toString() {
        return super.toString() + "[bonus=" + bonus + "]";
    }
}
