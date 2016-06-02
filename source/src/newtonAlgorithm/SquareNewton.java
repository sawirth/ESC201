package newtonAlgorithm;

public class SquareNewton {

    public static void main(String[] args) {
        double input = 581356487;
        double result = sqrt(input);
        System.out.println("Input: " + input + " || sqr: " + result + " || proof: " + result * result);
    }

    public static double sqrt(double c) {
        if (c < 0) return Double.NaN;
        double EPSILON = 1E-15;
        double t = c;
        int counter = 0;
        while (Math.abs(t - c/t) > EPSILON*t) {
            t = (c/t + t) / 2.0;
            counter++;
        }
        System.out.println("Iterations: " + counter);
        return t;
    }
}
