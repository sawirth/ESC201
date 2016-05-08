package sor;

import house3d.ColorMap;

import javax.swing.*;
import java.awt.*;

public class SOR_Panel extends JPanel {

    private double u[][];
    private int L;
    private int J;

    public SOR_Panel(double[][] u, int L, int J) {
        this.u = u;
        this.L = L;
        this.J = J;
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
    public Dimension getPreferredSize() {
        return new Dimension(J, L);
    }
}
