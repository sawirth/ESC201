package differential;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;



public class LotkaVolterraDemo extends JPanel {

    private static final long serialVersionUID = 1L;
    private JFreeChart phasenChart;
    private JFreeChart chart;

    public static void main(String[] args) {
        JFrame top = new JFrame("JFreeChart Test");
        top.setBounds(100, 100, 1600, 600);
        top.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        LotkaVolterraDemo jfc = new LotkaVolterraDemo();
        top.getContentPane().add(jfc);
        top.setVisible(true);
        jfc.run();
    }

    public LotkaVolterraDemo() {
        ChartPanel chartPanel = new ChartPanel(phasenChart);
        ChartPanel chartPanel1 = new ChartPanel(chart);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(chartPanel);
        add(chartPanel1);
    }

    public void run() {

        //initial values
        int n = 250;
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
            yn = RungeKutta.MidpointRK(LK, yn, h);
            e[i] = yn[0];
            a[i] = yn[1];

            series_a.add(t[i], a[i]);
            series_e.add(t[i], e[i]);
            series_ae.add(a[i], e[i]);
        }

        //XYLine Chart
        seriesCollection.addSeries(series_a);
        seriesCollection.addSeries(series_e);
        chart = ChartFactory.createXYLineChart("Entwicklung über die Zeit", "t", "e, a", seriesCollection);
        chart.setAntiAlias(true);

        // Phasenplot
        XYDataset phasenData = new XYSeriesCollection(series_ae);
        phasenChart = ChartFactory.createXYLineChart("Phasen-Plot", "a", "e", phasenData);
        phasenChart.setAntiAlias(true);

        repaint();
    }


    public void paint(Graphics g) {
        if (phasenChart != null) {
            Rectangle b = g.getClipBounds();
            phasenChart.draw((Graphics2D)g, new Rectangle2D.Double(b.x, b.y, b.width / 2, b.height));
        }

        if (chart != null) {
            Rectangle b = g.getClipBounds();
            chart.draw((Graphics2D) g, new Rectangle2D.Double(b.x + b.width / 2, b.y, b.width / 2, b.height));
        }
    }
}
