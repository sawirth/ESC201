package diffusion;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;

public class Diffusion extends JPanel {

    private JFreeChart chart;

    public Diffusion() {
        ChartPanel chartPanel = new ChartPanel(this.chart);
        add(chartPanel);
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Diffusion");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Diffusion diffPanel = new Diffusion();
        window.getContentPane().add(diffPanel);
        window.pack();
        window.setVisible(true);
        diffPanel.run();
    }

    private void run() {

        double u[] = new double[100];
        double lambda = 0.1;
        double deltaT = 4.9E-5;
        XYSeries series = new XYSeries(false);
        double L = 1;
        double N = 100;
        double deltaX = L / N;
        double D = 1;

        //Array initialisieren
        for (int i = 0; i < 100; i++) {
            if (Math.abs(deltaX * i - L / 2.0) <= lambda) {
                u[i] = 1 - (Math.pow(deltaX * i - L / 2.0, 2)) / Math.pow(lambda, 2);
            } else {
                u[i] = 0;
            }
            series.add(i, u[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        chart = ChartFactory.createXYLineChart("Diffusion", "x", "u", dataset);
        chart.setAntiAlias(true);

        //Auto range disable
        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRange(false);
        yAxis.setTickUnit(new NumberTickUnit(0.1));
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setRangeAxis(yAxis);

        repaint();

        //Ein Zeitstep berechnen und zeichnen
        long sleeptime = 50;
        double t = 0;
        double alpha = (D * deltaT) / (deltaX * deltaX);
        int counter = 0;
        while (t < 1) {

            series.clear();
            double u_old[] = new double[u.length];
            System.arraycopy(u, 0, u_old, 0, u.length);

            for (int j = 1; j < 99; j++) {
                u[j] = u_old[j] + alpha * (u_old[j + 1] - 2 * u_old[j] + u_old[j - 1]);
                series.add(j * deltaX, u[j]);
            }

            t += deltaT;
            repaint();
            counter++;

            try {
                Thread.sleep(sleeptime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        if (chart != null) {
            Rectangle r = g.getClipBounds();
            chart.draw((Graphics2D) g, new Rectangle2D.Double(r.x, r.y, r.width, r.height));
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 1000);
    }


}
