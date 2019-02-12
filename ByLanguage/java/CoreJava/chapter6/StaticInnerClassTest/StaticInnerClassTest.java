
public class StaticInnerClassTest {

    public static void main(String[] args) {
        double[] d = new double[20];
        for (int i = 0; i < d.length; i++) {
            d[i] = 100 * Math.random();
        }

        Pair p = ArrayAlg.minmax(d);
        System.out.println("min = " + p.first());
        System.out.println("max = " + p.second());
    }
}

class Pair {
    private double first;
    private double second;

    public Pair(double f, double s) {
        first = f;
        second = s;
    }

    public double first() {
        return first;
    }

    public double second() {
        return second;
    }
}

class ArrayAlg {
    public static Pair minmax(double[] values) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (double v: values) {
            if (min > v) min = v;
            if (max < v) max = v;
        }

        return new Pair(min, max);
    }
}
