package sor;


import house3d.ColorMap;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;

public class SOR extends JPanel{

    private double omega;
    private double u[][];
    private double R[][];
    private int J;
    private int L;

    public SOR(int J, int L, double omega) {
        this.J = J;
        this.L = L;
        this.omega = omega;

        initializeArrays();
        calculate();
    }

    private void initializeArrays() {
        u = new double[J][L];
        R = new double[J][L];

        int middleL = L / 2;
        int beginPlate = J / 4;
        int endPlate = (int) (J / 4.0 * 3);

        for (int l = 0; l < L; l++) {
            for (int j = 0; j < J; j++) {
                if (l == 0 || j == 0 || l == L - 1 || j == J - 1) {
                    //Rand
                    u[j][l] = 0;
                    R[j][l] = 0;
                } else if (l == middleL && j >= beginPlate && j <= endPlate) {
                    //Metallplatte
                    u[j][l] = 1000;
                    R[j][l] = 0;
                } else {
                    //Restliche felder
                    u[j][l] = 0;
                    R[j][l] = omega / 4.0;
                }
            }
        }
    }

    private void calculate() {

        //TODO: parallelisieren der Berechnung
        // Thread nimmt Parameter für minRow und maxRow

        double maxDelta = 0;
        int counter = 0;
        System.out.println("Start Calculating...");

        do {
            maxDelta = 0;
            double delta = 0;
            for (int l = 1; l < L - 1; l++) {
                for (int j = 1 + (l % 2); j < J - 1; j += 2) {
                    delta = calculateDelta(j, l);
                    u[j][l] = u[j][l] + delta;
                    maxDelta = Math.max(maxDelta, delta);
                }
            }

            for (int l = 1; l < L - 1; l++) {
                for (int j = 1 + ((1 + l) % 2); j < J - 1; j += 2) {
                    delta = calculateDelta(j, l);
                    u[j][l] += delta;
                    maxDelta = Math.max(maxDelta, delta);
                }
            }
            counter++;

        } while (maxDelta > 1);
        System.out.println("Finished Calculations: Iterations: " + counter);
        repaint();
    }

    private double calculateDelta(int j, int l) {
        return R[j][l] * (u[j + 1][l] + u[j - 1][l] + u[j][l + 1] + u[j][l - 1] - 4 * u[j][l]);
    }


    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (int l = 0; l < L; l++) {
            for (int j = 0; j < J; j++) {
                g2d.setColor(ColorMap.getRainbowColor(u[j][l] / 1000.0 ));
                g2d.drawLine(l, j, l, j);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}

