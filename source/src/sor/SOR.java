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

        for (int l = 0; l < L; l++) {
            for (int j = 0; j < J; j++) {
                if (l == 49 && j >= 25 && j <= 75) {
                    //Metallplatte
                    u[j][l] = 1000;
                    R[j][l] = 0;
                } else if (l == 0 || j == 0 || l == 99 || j == 99) {
                    //Rand
                    u[j][l] = 0;
                    R[j][l] = 0;
                } else {
                    //Restliche Felder
                    u[j][l] = 0;
                    R[j][l] = omega/4.0;
                }
            }
        }
    }

    private void calculate() {
        double maxDelta = 0;
        int counter = 0;
        System.out.println("Start Calculating...");

        do {
            double delta = 0;
            for (int l = 1; l < L - 1; l++) {
                for (int j = 1 + (l % 2); j < J - 1; j += 2) {
                    delta = calculateDelta(j, l);
                    u[j][l] += delta;
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
            System.out.println(maxDelta);
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (maxDelta > 1);
        System.out.println("Finished Calculations: Iterations: " + counter);
    }

    private double calculateDelta(int j, int l) {
        return R[j][l] * (u[j + 1][l] + u[j - 1][l] + u[j][l + 1] + u[j][l - 1] - 4 * u[j][l]);
    }

    private void print2DArray() {
        for (int l = 0; l < L; l++) {
            for (int j = 0; j < J; j++) {
                System.out.print(u[l][l] + " ");
            }
            System.out.println();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void paint(Graphics g) {
        for (int j = 0; j < this.J; j++) {
            for (int l = 0; l < this.L; l++) {
                drawPoint(g, j, l);
            }
        }
    }

    private void drawPoint(Graphics g, int j, int l) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(ColorMap.getRainbowColor(u[j][l]));
        g.drawLine(l, j, l, j);
        repaint();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        System.out.println("Start drawing...");

        for (int l = 0; l < L; l++) {
            for (int j = 0; j < J; j++) {
                g2d.setColor(ColorMap.getRainbowColor(u[l][j] / J));
                g2d.drawLine(l, j, l, j);
            }
        }

        System.out.println("Finished drawing");
    }
}

