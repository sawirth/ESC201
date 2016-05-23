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
import java.awt.geom.Rectangle2D;

public class CIR extends JPanel{

    private JFreeChart chart;

    public CIR() {
        ChartPanel chartPanel = new ChartPanel(this.chart);
        add(chartPanel);
    }

    public static void main(String args[]) {
        JFrame window = new JFrame("Hydrodynamik");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        CIR cirPanel = new CIR();
        window.getContentPane().add(cirPanel);
        window.pack();
        window.setVisible(true);
        cirPanel.run();
    }

    private void run() {
        double alpha = 1.0;
        double beta = 8.0;
        double a = 1;
        int N = 100;
        double deltaX = 2.0 / N;
        double deltaT = 0.016;
        double c = deltaT / deltaX;
        double gauss_u_cir[] = new double[N];
        double gauss_u_lax[] = new double[N];

        double rect_u_cir[] = new double[N];
        double rect_u_lax[] = new double[N];

        XYSeries gauss_cir_series = new XYSeries("Gauss CIR", false);
        XYSeries gauss_lax_series = new XYSeries("Gauss LAX", false);

        XYSeries rect_cir_series = new XYSeries("Rectangle CIR", false);
        XYSeries rect_lax_series = new XYSeries("Rectangle LAX", false);

        //Array initialisieren
        for (int i = 0; i < N; i++) {

            //Gauss
            gauss_u_cir[i] = alpha * Math.exp(- beta * Math.pow((deltaX * i) - 1, 2));
            gauss_cir_series.add(i * deltaX, gauss_u_cir[i]);

            gauss_u_lax[i] = gauss_u_cir[i];
            gauss_lax_series.add(i * deltaX, gauss_u_cir[i]);


            //Rechteck
            if (deltaX * i <= 0.3 || deltaX * i >= 0.7) {
                rect_u_cir[i] = 0;
                rect_u_lax[i] = 0;
            } else {
                rect_u_cir[i] = 1;
                rect_u_lax[i] = 1;
            }

            rect_cir_series.add(i * deltaX, rect_u_cir[i]);
            rect_lax_series.add(i * deltaX, rect_u_lax[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(gauss_cir_series);
        dataset.addSeries(gauss_lax_series);
        dataset.addSeries(rect_cir_series);
        dataset.addSeries(rect_lax_series);
        chart = ChartFactory.createXYLineChart("Wellen", "x", "y", dataset);
        chart.setAntiAlias(true);

        //Auto range disable
        NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRange(false);
        yAxis.setTickUnit(new NumberTickUnit(0.1));
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setRangeAxis(yAxis);

        repaint();

        //Veränderung
        while (true) {

            //Reset for next iteration
            double gauss_u_cir_old[] = new double[N];
            double gauss_u_lax_old[] = new double[N];
            double rect_u_cir_old[] = new double[N];
            double rect_u_lax_old[] = new double[N];

            for (int i = 0; i < N; i++ ) {
                gauss_u_cir_old[i] = gauss_u_cir[i];
                gauss_u_lax_old[i] = gauss_u_lax[i];
                rect_u_cir_old[i] = gauss_u_cir[i];
                rect_u_lax_old[i] = gauss_u_lax[i];
            }

            gauss_cir_series.clear();
            gauss_lax_series.clear();
            rect_cir_series.clear();
            rect_lax_series.clear();

            //Calculation
            for (int j = 1; j < N - 1; j++) {

                //Gauss
                gauss_u_cir[j] = getCIRvalue(c, gauss_u_cir_old, j);
                gauss_cir_series.add(deltaX * j, gauss_u_cir[j]);
                gauss_u_lax[j] = getLaxWendroffValue(c, gauss_u_lax_old, j);
                gauss_lax_series.add(deltaX * j, gauss_u_lax[j]);

                //Rectangle
                rect_u_cir[j] = getCIRvalue(c, rect_u_cir_old, j);
                rect_cir_series.add(deltaX * j, rect_u_cir[j]);
                rect_u_lax[j] = getLaxWendroffValue(c, rect_u_lax_old, j);
                rect_lax_series.add(deltaX * j, rect_u_lax[j]);
            }

            gauss_u_cir[N - 1] = gauss_u_cir[1];
            gauss_u_cir[0] = gauss_u_cir[N - 2];

            gauss_u_lax[N - 1] = gauss_u_lax[1];
            gauss_u_lax[0] = gauss_u_lax[N - 2];

            //Paint and wait
            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private double getLaxWendroffValue(double c, double[] u_lax_old, int j) {
        return 1 / 2.0 * c * (1 + c) * u_lax_old[j - 1] + (1.0 - c * c) * u_lax_old[j] - 1 / 2.0 * c * (1 - c) * u_lax_old[j + 1];
    }

    private double getCIRvalue(double c, double[] u_cir_old, int j) {
        return u_cir_old[j] - c * (u_cir_old[j] - u_cir_old[j - 1]);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 800);
    }


    @Override
    public void paint(Graphics g) {
        if (chart != null) {
            Rectangle r = g.getClipBounds();
            chart.draw((Graphics2D) g, new Rectangle2D.Double(r.x, r.y, r.width, r.height));
        }
    }
}
