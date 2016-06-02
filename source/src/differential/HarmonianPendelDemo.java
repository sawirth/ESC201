package differential;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HarmonianPendelDemo extends JPanel{

    private JFreeChart chart;

    public static void main(String[] args) {
        JFrame top = new JFrame("Leap-Frog");
        top.setBounds(100, 0, 1300, 1000);
        top.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        HarmonianPendelDemo harmonianPendelDemo = new HarmonianPendelDemo();
        top.getContentPane().add(harmonianPendelDemo);
        top.setVisible(true);
        harmonianPendelDemo.run();
    }

    public void run() {

        XYSeriesCollection seriesCollection = new XYSeriesCollection();

        double p = 0;
        double q = 0;
        double h = 0;

        /*
        HARMONIAN OSCILLATOR
         */

        //initial values
        double p0 = 1;
        double q0 = 1;
        double h0 = 0.1;

        for (int j = 0; j < 4; j++) {
            XYSeries harmonianSet = new XYSeries("Harmonian " + j, false);

            p = p0 + j;
            q = q0;
            h = h0 + 0.4 * j;

            for (int i = 0; i < 1000/(j + 1); i++) {
                q = q + 0.5 * h * p;
                p = p - h * q;
                q = q + 0.5 * h * p;

                harmonianSet.add(p, q);
            }

            seriesCollection.addSeries(harmonianSet);
        }


        double[] t = new double[1000];
        double[] yn = new double[2];
        HarmonianRK HRK = new HarmonianRK();
        XYSeries HRK_series = new XYSeries("HRK");

        p = 1;
        q = 1;
        h = 0.1;
        for (int i = 0; i < 1000; i++) {
            t[i] = i * h;
            yn = RungeKutta.MidpointRK(HRK, yn, h);
            HRK_series.add(yn[0], yn[1]);
        }

//        seriesCollection.addSeries(HRK_series);

        /*
        PENDEL
         */

        //initial values
        p0 = 1;
        q0 = 1;
        h = 0.1;

        for (int j = 0; j < 6; j++) {
            XYSeries pendel = new XYSeries("Pendel  " + j, false);

            p = p0;
            q = q0 + 0.5 * j;

            for (int i = 0; i < 200; i++) {
                q = q + 0.5 * h * p;
                p = p - h * (Math.sin(q));
                q = q + 0.5 * h * p;

                pendel.add(q % (2 * Math.PI), p);
            }

            seriesCollection.addSeries(pendel);
        }

//        seriesCollection.removeAllSeries();



//        chart = ChartFactory.createXYLineChart("Pendel Plot", "p", "q", pendelSets);
//        chart = ChartFactory.createXYLineChart("Harmonian Plot", "p", "q", harmonianSetCollection);
        chart = ChartFactory.createXYLineChart("Harmonian RK", "p", "q", seriesCollection);

        repaint();
    }

    public void paint(Graphics g) {
        if (chart != null) {
            Rectangle b = g.getClipBounds();
            chart.draw((Graphics2D) g, new Rectangle2D.Double(b.x, b.y, b.width, b.height));
        }
    }

}
