package house3d;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class House3D extends JPanel{

    private List<Point3D> points;
    private double ds;
    private double dO;

    public House3D() {
        points = new ArrayList<Point3D>();

        ds = 9;
        dO = 10;

        //Start Points
        points.add(new Point3D(0.5, 0, 1.5));
        points.add(new Point3D(0, 0, 1));
        points.add(new Point3D(0, 0, 0));
        points.add(new Point3D(1, 0, 0));
        points.add(new Point3D(1, 0, 1));
        points.add(new Point3D(0.5, 1, 1.5));
        points.add(new Point3D(0, 1, 1));
        points.add(new Point3D(0, 1, 0));
        points.add(new Point3D(1, 1, 0));
        points.add(new Point3D(1, 1, 1));
    }

    public void rotateX(double angle) {
        for (Point3D point : points) {
            point.rotateY(angle);
        }
    }


    private void doDrawing(Graphics g, List<Point3D> points) {

        Graphics2D g2d = (Graphics2D) g;

        //Paints all lines of the house
        for (int i = 0; i < 4; i++) {
            drawLine(points.get(i), points.get(i + 1), g2d);
            drawLine(points.get(i + 5), points.get(i + 6), g2d);
            drawLine(points.get(i), points.get(i + 5), g2d);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g, this.points);
    }


    private void drawLine(Point3D point1, Point3D point2, Graphics2D g) {
        int x1 = 300 + 400 * (int) Math.round((this.ds / (this.dO + point1.y)) * point1.x);
        int y1 = 300 + 400 * (int) Math.round((this.ds / (this.dO + point1.y)) * point1.z);

        int x2 =  300 + 400 * (int) Math.round((this.ds / (this.dO + point2.y)) * point2.x);
        int y2 =  300 + 400 * (int) Math.round((this.ds / (this.dO + point2.y)) * point2.z);

        g.drawLine(x1, y1, x2, y2);
    }



    class Point3D {
        double x, y, z;

        public Point3D(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void rotateY(double angle) {
            this.x = Math.cos(angle) * this.x + Math.sin(this.x);
            this.z = -Math.sin(angle) * this.z + Math.cos(angle) * this.z;
        }
    }

}
