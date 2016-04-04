package newtonAlgorithm;

/*
** my_class_example.java should provide a basic program structure for a GUI
** program in Java that can be used by the students of the SPIN course if they
** have problems with their code.
**
** First the class name has to be replaced with the name of your class and
** the paint() and run() functions must be adapted to your needs.
*/

/* Importing packages needed. */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Newton extends JPanel {

    double E;
    double M;

	/* Define global constants, e.g. the window size (in pixels). */
    final static int X_WINDOW_SIZE = 800;
    final static int Y_WINDOW_SIZE = 600;

    /* Upper left corner of the window on our screen. */
    final static int X_WINDOW_LOCATION = 100;
    final static int Y_WINDOW_LOCATION = 100;

    /* Define gobal variables like the X and Y positions of a particle. */
    double X = 0.0;
    double Y = 0.0;

    /* Define max. values for X and Y  to rescale them to the screen in paint(). */
    double X_MAX = 1.0;
    double Y_MAX = 1.0;

    public static void main(String[] args) {
		/* Create a new JFrame object which provides a very basic window for the
		 * application. The title can be chosen arbitrarily. */
        JFrame top = new JFrame("Kepler - Newton");

		/* Position and size of the window. */
        top.setBounds(X_WINDOW_LOCATION, Y_WINDOW_LOCATION, X_WINDOW_SIZE, Y_WINDOW_SIZE);

		/* What do we do if we get a close signal? */
        top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Make a new instance of our class my_class_example. */
        Newton my_class_instance = new Newton();

		/* Add the JPanel to the window we created. All graphical output is now
		 * displayed in the window. */
        top.getContentPane().add(my_class_instance);

		/* Make the window visible. */
        top.setVisible(true);

		/* Run the main function run() where we start the simulation. */
        my_class_instance.run();
    }

    public void paint(Graphics g) {
        int xs, ys, xs_max, ys_max;
        // clear drawing area
		/* Get the area that we want to redraw. */
        Rectangle bounds = getBounds();

		/* Maybe rescale the variables to the screen size? */
        xs_max = X_WINDOW_SIZE;
        ys_max = Y_WINDOW_SIZE;

        xs = (int)(X/X_MAX)*xs_max;
        ys = (int)(Y/Y_MAX)*ys_max;

        //Calculate screen coordinates and paint them
    }


    public double newton(IFunction f, IFunction df, double x0) {
        double x1 = x0;

        while (Math.abs(x1- x0 / x1) < 0.1) {
            x1 = x0 - f.f(x0)/df.f(x0);
            x0 = x1;
        }

        return x1;
    }

    public void run() {
		/* First initialize variables (if you did not do it before). */
        X = 0.0;
        Y = 0.0;

        fKepler f = new fKepler(0.5);
        dfKepler df = new dfKepler(0.5);

        while (true) {
            // Calculate E

            //

			/* Send a paint message to repaint the window. */
            repaint();

			/* Short delay so we can see how the program evolves. */
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {}
        }
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
}
