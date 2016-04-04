package newtonAlgorithm;

public class SquareNewton {

    public static void main(String[] args) {
        System.out.println(sqrt(12));
    }

    public static double sqrt(double c) {
        if (c < 0) return Double.NaN;
        double EPSILON = 1E-15;
        double t = c;
        while (Math.abs(t - c/t) > EPSILON*t)
            t = (c/t + t) / 2.0;
        return t;
    }
}
