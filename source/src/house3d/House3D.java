package house3d;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class House3D extends JPanel{

    private List<Point3D> points;
    private double ds;
    private double d0;
    private int offset;
    private int scale;

    public House3D(double ds, double d0, int offset, int scale) {
        points = new ArrayList<>();

        this.ds = ds;
        this.d0 = d0;
        this.offset = offset;
        this.scale = scale;

        //Front
        points.add(new Point3D(0, 0, 0));
        points.add(new Point3D(1, 0, 0));
        points.add(new Point3D(1, 1, 0));
        points.add(new Point3D(0, 1, 0));
        points.add(new Point3D(0.5, 1.5, 0));

        //Back
        points.add(new Point3D(0, 0, 1));
        points.add(new Point3D(1, 0, 1));
        points.add(new Point3D(1, 1, 1));
        points.add(new Point3D(0, 1, 1));
        points.add(new Point3D(0.5, 1.5, 1));
    }

    public void rotateZ(double angle) {
        for (Point3D point : points) {
            point.rotateZ(angle);
        }
    }

    public void rotateX(double angle) {
        for (Point3D point : points) {
            point.rotateX(angle);
        }
    }

    public void rotateY(double angle) {
        for (Point3D point : points) {
            point.rotateY(angle);
        }
    }


    private void doDrawing(Graphics g, List<Point3D> points) {

        Graphics2D g2d = (Graphics2D) g;

        //Front
        drawLine(points.get(0), points.get(1), g2d, Color.RED);
        drawLine(points.get(1), points.get(2), g2d, Color.RED);
        drawLine(points.get(2), points.get(3), g2d, Color.RED);
        drawLine(points.get(0), points.get(3), g2d, Color.RED);
        drawLine(points.get(2), points.get(4), g2d, Color.RED);
        drawLine(points.get(3), points.get(4), g2d, Color.RED);

        //Back
        drawLine(points.get(5), points.get(6), g2d, Color.BLUE);
        drawLine(points.get(6), points.get(7), g2d, Color.BLUE);
        drawLine(points.get(7), points.get(8), g2d, Color.BLUE);
        drawLine(points.get(5), points.get(8), g2d, Color.BLUE);
        drawLine(points.get(7), points.get(9), g2d, Color.BLUE);
        drawLine(points.get(8), points.get(9), g2d, Color.BLUE);

        //Side and Top
        drawLine(points.get(4), points.get(9), g2d, Color.GREEN);
        drawLine(points.get(3), points.get(8), g2d, Color.GREEN);
        drawLine(points.get(2), points.get(7), g2d, Color.GREEN);
        drawLine(points.get(1), points.get(6), g2d, Color.GREEN);
        drawLine(points.get(0), points.get(5), g2d, Color.GREEN);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g, this.points);
    }


    private void drawLine(Point3D point1, Point3D point2, Graphics2D g, Color color) {
        double x1 = this.offset + this.scale * (this.ds / (this.d0 + point1.z)) * point1.x;
        double y1 = this.offset + this.scale * (this.ds / (this.d0 + point1.z)) * point1.y;

        double x2 = this.offset + this.scale *(this.ds / (this.d0 + point2.z)) * point2.x;
        double y2 = this.offset + this.scale *(this.ds / (this.d0 + point2.z)) * point2.y;

        g.setColor(color);
        g.drawLine((int) x1, (int) y1, (int)x2, (int) y2);
    }


    class Point3D {
        double x, y, z;

        public Point3D(double x, double y, double z) {
            this.x = x;
            this.y = -y;
            this.z = z;
        }

        public void rotateZ(double angle) {
            angle = Math.toRadians(angle);

            double tempX = this.x;
            double tempY = this.y;

            this.x = Math.cos(angle) * tempX+ Math.sin(angle) * tempY;
            this.y = -Math.sin(angle) * tempX + Math.cos(angle) * tempY;
        }

        public void rotateX(double angle) {
            angle = Math.toRadians(angle);

            double tempY = this.y;
            double tempZ = this.z;

            this.y = Math.cos(angle) * tempY + Math.sin(angle) * tempZ;
            this.z = -Math.sin(angle) * tempY + Math.cos(angle) * tempZ;
        }

        public void rotateY(double angle) {
            angle = Math.toRadians(angle);

            double tempX = this.x;
            double tempZ = this.z;

            this.x = Math.cos(angle) * tempX + Math.sin(angle) * tempZ;
            this.z = -Math.sin(angle) * tempX + Math.cos(angle) * tempZ;
        }
    }
}
