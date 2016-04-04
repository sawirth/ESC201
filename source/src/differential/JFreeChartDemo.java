package differential;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javafx.util.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import sun.org.mozilla.javascript.internal.Function;


public class JFreeChartDemo extends JPanel {

    private static final long serialVersionUID = 1L;
    private JFreeChart chart;

    public static void main(String[] args) {
        JFrame top = new JFrame("JFreeChart Test");
        top.setBounds(100, 100, 800, 600);
        top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFreeChartDemo jfc = new JFreeChartDemo();
        top.getContentPane().add(jfc);
        top.setVisible(true);
        jfc.run();
    }

    public void run() {

        //initial values
        int n = 300;
        double e0 = 1.06 / 0.02;
        double a0 = 2. / 0.01;
        double h = 0.1;

        //arrays for storing the values
        double[] a = new double[n];
        double[] e = new double[n];
        double[] t = new double[n];
        double[] yn = new double[2];

        //start values
        yn[0] = e0;
        yn[1] = a0;

        LotkaVoltera LK = new LotkaVoltera();

        XYSeriesCollection seriesCollection = new XYSeriesCollection();
        XYSeries series_a = new XYSeries("a(t)");
        XYSeries series_e = new XYSeries("e(t)");
        XYSeries series_ae = new XYSeries("a(e)", false);

        System.out.println("Start: e = " + e[0] + " || a = " + a[0]);
        for (int i = 0; i < t.length; i++) {
            t[i] = i * h;
            yn = RungeKutta.MidpointRK(LK, t[i], yn, h);
            e[i] = yn[0];
            a[i] = yn[1];

            series_a.add(t[i], a[i]);
            series_e.add(t[i], e[i]);
            series_ae.add(a[i], e[i]);
        }

        //XYLine Chart
        seriesCollection.addSeries(series_a);
        seriesCollection.addSeries(series_e);
//        chart = ChartFactory.createXYLineChart("Plot", "t", "e, a", seriesCollection);

        // Phasenplot
        XYDataset phasenData = new XYSeriesCollection(series_ae);
        chart = ChartFactory.createXYLineChart("Plot", "a", "e", phasenData);

        repaint();
    }


    public void paint(Graphics g) {
        if (chart != null) {
            Rectangle b = g.getClipBounds();
            chart.draw((Graphics2D)g, new Rectangle2D.Double(b.x, b.y, b.width, b.height));
        }
    }
}
