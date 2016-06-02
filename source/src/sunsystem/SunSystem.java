package sunsystem;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class SunSystem extends JPanel {
    private static final long serialVersionUID = 1L;
    private JFreeChart chart;
    private XYSeriesCollection planetData;

    // GMsun = k^2
    private final double k = 0.01720209895;

    //Datum = 18.03.08 00:00:00.0000,    2454543.500000000 = A.D.
    private double day = 2454543.500000000;

    public static void main(String[] args) {
        JFrame top = new JFrame("Sunsystem");
        top.setBounds(100, 100, 1300, 1000);
        top.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        SunSystem sunSystem = new SunSystem();
        top.getContentPane().add(sunSystem);
        top.setVisible(true);
        sunSystem.run();
    }

    private void run() {
        //List containing all planets including the sun
        ArrayList<Planet> planets = getPlanetList();
        planetData = new XYSeriesCollection();
        int N = planets.size();
        int h = 2;
        int time = 0;
        int timeEnd = 10000;

        for (int i = 0; i < N; i++) {
            XYSeries series = new XYSeries(planets.get(i).name, false);
            planetData.addSeries(series);
        }

        XYSeriesCollection data = new XYSeriesCollection();
        for (int i = 0; i < 5; i++) {
            data.addSeries(planetData.getSeries(i));
        }

        chart = ChartFactory.createXYLineChart("Solar system", "x", "y", data);
        repaint();
        while (time < timeEnd) {
            calculateOneTimeStep(planets, N, h);
            time += h;
            repaint();
        }
    }

    private void calculateOneTimeStep(ArrayList<Planet> planets, int n, int h) {
        for (Planet planet : planets) {
            //First Leap-Frog
            for (int k = 0; k < 3; k++) {
                planet.r[k] += 0.5 * h * planet.v[k];
            }
        }

        calculateF(planets, n);

        for (Planet planet : planets) {
            //Second Leap-Frog step
            for (int k = 0; k < 3; k++) {
                planet.v[k] += h * (planet.getF().get(k) / planet.mass);
            }
        }

        int i = 0;
        for (Planet planet : planets) {
            //Third Leap-Frog step
            for (int k = 0; k < 3; k++) {
                planet.r[k] += 0.5 * h * planet.v[k];
                planetData.getSeries(i).add(planet.getXZ()[0], planet.getXZ()[1]);
            }
            i++;
        }
    }

    private void calculateF(ArrayList<Planet> planets, int n) {
        for (Planet planet : planets) {
            planet.initializeF();
        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {

                double[] d = new double[3];
                double d2 = 0;

                for (int k = 0; k < 3; k++) {
                    d[k] = planets.get(j).r[k] - planets.get(i).r[k];
                    d2 += d[k] * d[k];
                }

                double ir = 1.0 / Math.sqrt(d2);
                double ir3 = ir * ir * ir;
                ir3 *= planets.get(i).mass * planets.get(j).mass;
                ir3 *= (k * k);

                for (int k = 0; k < 3; k++) {
                    Planet pi = planets.get(i);
                    Planet pj = planets.get(j);

                    pi.getF().set(k, pi.getF().get(k) + ir3 * d[k]);
                    pj.getF().set(k, pj.getF().get(k) - ir3 * d[k]);
                }
            }
        }
    }

    public void paint(Graphics g) {
        if (chart != null) {
            Rectangle b = g.getClipBounds();
            chart.draw((Graphics2D)g, new Rectangle2D.Double(b.x, b.y, b.width, b.height));
        }
    }

    private ArrayList<Planet> getPlanetList() {
        ArrayList<Planet> list = new ArrayList<Planet>();
        list.add(sonne);
        list.add(merkur);
        list.add(venus);
        list.add(erdemond);
        list.add(mars);
        list.add(saturn);
        list.add(jupiter);
        list.add(uranus);
        list.add(neptun);
        list.add(pluto);

        return list;
    }

    //inner class for all planets
    class Planet {
        static final int F_SIZE = 3;
        String name;
        double mass;
        double r[];
        double v[];
        private ArrayList<Double> F;

        Planet(String name, double mass, double r[], double[] v) {
            this.name = name;
            this.r = r.clone();
            this.v = v.clone();
            this.mass = mass;
            this.F = new ArrayList<Double>(F_SIZE);

            initializeF();
        }

        public ArrayList<Double> getF() {
            return F;
        }

        public double[] getXY(){
            double[] d = {r[0], r[1]};
            return d;
        }

        public double[] getXZ() {
            double[] d = {r[0], r[2]};
            return d;
        }

        /**
         * Set all F of planet to 0
         */
        private void initializeF() {
            for (int i = 0; i < F_SIZE; i++) {
                this.F.add(i, new Double(0));
            }
        }
    }


    Planet sonne = new Planet("Sonne", 1.0,
            new double[] {-3.402962055191472E-04, 4.973801475460748E-03,-6.230136454608636E-05},
            new double[] {-6.477668621023070E-06,-1.292580409219799E-07, 1.154585032962209E-07});
    Planet merkur = new Planet("Merkur",  1.0/6023600,
            new double[] { 3.704735169720974E-02,-4.529211095852149E-01,-4.090255306376755E-02},
            new double[] { 2.239183874467135E-02, 3.736008439029809E-03,-1.750026916388115E-03});
    Planet venus = new Planet("Venus", 1.0/408523.61,
            new double[] { 4.272157290820016E-01,-5.835752726996720E-01,-3.279422047835795E-02},
            new double[] { 1.622328987696207E-02, 1.181629954076840E-02,-7.748242818668279E-04});
    Planet erdemond = new Planet("Erde", 1.0/332946.050895,
            new double[] {-9.948486972722731E-01, 4.564231864395614E-02,-6.099525188647536E-05},
            new double[] {-9.901408163678924E-04,-1.725450198017297E-02, 4.346241632445323E-07});
    Planet mars = new Planet("Mars", 1.0/3098708,
            new double[] {-1.093539305796724E+00, 1.240381444357973E+00, 5.266905384900308E-02},
            new double[] {-9.958470942542191E-03,-8.082316351751016E-03, 7.520708651107539E-05});
    Planet jupiter = new Planet("Jupiter", 1.0/1047.3486,
            new double[] { 7.199075962861715E-01,-5.164765414047316E+00, 5.281301305052329E-03},
            new double[] { 7.380707839356994E-03, 1.399344086177555E-03,-1.710023430451413E-04});
    Planet saturn = new Planet("Saturn", 1.0/3497.898,
            new double[] {-8.469664737705321E+00, 3.804527121928150E+00, 2.708474727487031E-01},
            new double[] {-2.583089539225567E-03,-5.101976771205786E-03, 1.915567842416982E-04});
    Planet uranus = new Planet("Uranus", 1.0/22902.98,
            new double[] { 1.970001443062262E+01,-3.956376098536538E+00,-2.699288868040702E-01},
            new double[] { 7.457402740600397E-04, 3.672797797089092E-03, 3.988780204062304E-06});
    Planet neptun = new Planet("Neptun", 1.0/19412.24,
            new double[] { 2.361441531200179E+01,-1.856288724958460E+01,-1.619425696998957E-01},
            new double[] { 1.919278314221217E-03, 2.486348363416270E-03,-9.543324154983418E-05});
    Planet pluto = new Planet("Pluto", 1.0/135200000,
            new double[] {-4.656585770964581E-01,-3.123136435608064E+01, 3.476634650132539E+00},
            new double[] { 3.195935187090267E-03,-6.368370346118488E-04,-8.563102596919757E-04});
}

