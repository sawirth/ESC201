package newtonAlgorithm;

import javax.swing.*;

public class Algo extends JPanel {

    // M = 2*pi*t/T
    private double M = 0;
    private double E;
    private double T = 1;
    private double t;

    /* Define global constants, e.g. the window size (in pixels). */
    final static int X_WINDOW_SIZE = 800;
    final static int Y_WINDOW_SIZE = 600;

    /* Upper left corner of the window on our screen. */
    final static int X_WINDOW_LOCATION = 100;
    final static int Y_WINDOW_LOCATION = 100;


    public double newton(IFunction f, IFunction df, double x0) {
        double x1 = x0;

        while (Math.abs(x1- x0 / x1) > 0.1) {
            x1 = x0 - f.f(x0)/df.f(x0);
            x0 = x1;
        }

        return x1;
    }

    class fKepler implements IFunction {
        private double e;

        public fKepler(double e) {
           this.e = e;
        }

        public double f(double x) {
            return E - this.e * Math.sin(E) - M;
        }
    }

    class dfKepler implements IFunction {
        private double e;

        public dfKepler(double e) {
            this.e = e;
        }

        public double f(double x) {
            return 1 - this.e * Math.cos(E);
        }
    }

    public static void main(String[] args) {

        JFrame top = new JFrame("Newton");

		/* Position and size of the window. */
        top.setBounds(X_WINDOW_LOCATION, Y_WINDOW_LOCATION, X_WINDOW_SIZE, Y_WINDOW_SIZE);

		/* What do we do if we get a close signal? */
        top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Algo algo1 = new Algo();
        top.getContentPane().add(algo1);
        top.setVisible(true);

    }
}
