package fractals;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class LogisticMap extends JPanel {

    private static final long serialVersionUID = 1L;
    private JFreeChart chart;

    public static void main(String[] args) {
        JFrame top = new JFrame("LogisticMap");
        top.setBounds(100, 100, 1300, 1000);
        top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LogisticMap logisticMap = new LogisticMap();
        top.getContentPane().add(logisticMap);
        top.setVisible(true);
        logisticMap.run();
    }

    private void run() {
        double a = 0;
        double accuracy = 0.0004;
        double x_n = 0.9;
        int iterations = 1000;

        XYSeries series = new XYSeries(false);

        while (a <= 4) {
            //start drawing at this point
            if (a >= 2.7) {
                series = calcAValues(series, a, x_n, iterations);
            }
            a = a + accuracy;
        }

        //draw the chart
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        chart = ChartFactory.createScatterPlot("LogisticMap", "a", "x_n", dataset);
        XYPlot plot = chart.getXYPlot();
        XYDotRenderer renderer = new CustomRenderer(1, 1);
        plot.setRenderer(renderer);
        repaint();
    }


    /**
     * Calulates all x values for a given a and adds it to the given series
     * @param series Series containing all values for one plot
     * @param a
     * @param x_n Start value of x at the position of a
     * @param iterations Defines the amount of iterations until the calculation stops
     * @return
     */
    private XYSeries calcAValues(final XYSeries series, final double a, double x_n, final int iterations) {
        for (int i = 0; i < iterations; i++) {
            x_n = calcXn(a, x_n);
            if (i > 500) {      //only start adding values after 500 iterations
                series.add(a, x_n);
            }
        }
        return series;
    }

    private double calcXn(double a, double x_n) {
        return a * x_n * (1 - x_n);
    }

    public void paint(Graphics g) {
        if (chart != null) {
            Rectangle b = g.getClipBounds();
            chart.draw((Graphics2D)g, new Rectangle2D.Double(b.x, b.y, b.width, b.height));
        }
    }


    //inner class to style the dots of the chart
    class CustomRenderer extends XYDotRenderer {

        public CustomRenderer(int w, int h) {
            super();
            setDotWidth(w);
            setDotHeight(h);
        }
    }
}
