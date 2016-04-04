package examples;

/* Importing packages needed. */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

/******************************************************************************
 This is our main class. It extends JPanel so we can use the drawing
 functions provided by this class.
 ******************************************************************************/
public class GameOfLife extends JPanel {
    /******************************************************************************
     Define global constants and variables that can be seen by all functions in
     the class.
     ******************************************************************************/

	/* Define global constants, e.g. the window size (in pixels). */
    final static int X_WINDOW_SIZE = 800;
    final static int Y_WINDOW_SIZE = 600;

    /* Upper left corner of the window on our screen. */
    final static int X_WINDOW_LOCATION = 100;
    final static int Y_WINDOW_LOCATION = 100;

    /* Define the two possible states DEAD and ALIVE. */
    final int DEAD = 0;
    final int ALIVE = 1;

    /* Define how many grid points we have and the size of a pixel. */
    final int GRID_SIZE = 100;
    final int PIXEL_SIZE = 5;

    /* Define gobal variables, in this case the two grids. */
    int grid1[][] = new int[GRID_SIZE][GRID_SIZE];
    int grid2[][] = new int[GRID_SIZE][GRID_SIZE];

    /************************* end global variables ******************************/

    /******************************************************************************
     The main function is called when we start the program (It has to be static!).
     ******************************************************************************/
    public static void main(String[] args) {
		/* Create a new JFrame object which provides a very basic window for the
		 * application. The title can be chosen arbitrarily. */
        JFrame top = new JFrame("Game of Life");

		/* Position and size of the window. */
        top.setBounds(X_WINDOW_LOCATION, Y_WINDOW_LOCATION, X_WINDOW_SIZE, Y_WINDOW_SIZE);

		/* What do we do if we get a close signal? */
        top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* Make a new instance of our class GameOfLife. */
        GameOfLife gol1 = new GameOfLife();

		/* Add it to the window we created. All graphical output is now
		 * displayed in the window. */
        top.getContentPane().add(gol1);

		/* Make the window visible. Do this last otherwise your risk that you have no output on your screen. */
        top.setVisible(true);

		/* Run the main loop. */
        gol1.run();
    }
    /******************************* end main ***********************************/

    /******************************************************************************
     The function paint() is called whenever the event handler of the program gets
     a message that tells it to repaint the JPanel (e.g. we have another window
     that was overlapping with it). It is also exectued when we use the repaint()
     function somewhere in the code.
     ******************************************************************************/
    public void paint(Graphics g) {
		/* Get the area that we want to redraw. */
        Rectangle bounds = getBounds();

		/* Clear the area (sometimes you might not want this so just comment it if not needed. */
        g.clearRect(bounds.x, bounds.y, bounds.width, bounds.height);

		/* Draw the grid. */
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                int cell = grid1[i][j];
                if (cell == ALIVE) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.BLUE);
                }
                int x = PIXEL_SIZE * i;
                int y = PIXEL_SIZE * j;
                g.fillRect(x, y, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }
    /****************************** end paint ************************************/

    /******************************************************************************
     This function decides if a cell is DEAD or ALIVE using the random number
     generator Math.random().
     ******************************************************************************/
    public int random_cell() {
        if (Math.random() < 0.1) {
            return ALIVE;
        } else {
            return DEAD;
        }
    }

    /******************************************************************************
     The function run() is called from the function main(). Here we implement the
     main loop that does the actual calculations.
     ******************************************************************************/
    public void run() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (i == 0 || j == 0 || i == GRID_SIZE-1 || j == GRID_SIZE-1) {
                    grid1[i][j] = DEAD;
                } else {
                    grid1[i][j] = random_cell();
                }
            }
        }

		/* Main loop of the program. It continues until the user closes the window. */
        while (true) {
			/* Apply rules. */
            for  (int i = 1; i < GRID_SIZE - 1; i++) {
                for  (int j = 1; j < GRID_SIZE - 1; j++) {
                    int neighbors = grid1[i-1][j-1] + grid1[i][j-1] + grid1[i+1][j-1];
                    neighbors += grid1[i-1][j] + grid1[i+1][j];
                    neighbors += grid1[i-1][j+1] + grid1[i][j+1] + grid1[i+1][j+1];
                    int cell = grid1[i][j];
                    if (cell == DEAD && neighbors == 3) {
                        grid2[i][j] = ALIVE;
                    } else if (cell == ALIVE && (neighbors < 2 || neighbors > 3)) {
                        grid2[i][j] = DEAD;
                    } else {
                        grid2[i][j] = grid1[i][j];
                    }
                }
            }

			/* Swap the two grids. */
            int[][] grid_temp = grid1;
            grid1 = grid2;
            grid2 = grid_temp;

			/* Calls paint(). */
            repaint();

			/* Short delay so we can see how the program evolves. */
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {}
        }
    }
    /********************************** end run **********************************/
}
/******************************* end class ***********************************/
