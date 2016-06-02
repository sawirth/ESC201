import java.awt.*;
import java.applet.*;

// simple version: thread, but no double buffering

public class Lorenzt extends java.applet.Applet implements Runnable {
    float x1, x2, x3, y1, y2, y3, h, u1, u2, v1, v2, c, cs;
    int width, height;
    float hue = 0;

    public void init() {
        setSize(750, 750);
        height = getSize().height;
        width = getSize().width;
        setBackground(Color.black);
        setForeground(Color.white);
        cs = 800.F;
        c = 26.5F;
        h = 0.007F;
        x1 = 0.F;
        x2 = 1.F;
        x3 = 20.F;
    }

    private Thread thr = null;

    public void start() {
        if (thr == null) {
            thr = new Thread(this);
            thr.start();
        }
    }

    public void stop() {
        if ((thr != null) && thr.isAlive())
            thr.interrupt();
        thr = null;
    }

    public void paint(Graphics g) {
        hue = (hue + 3e-5F) % 1.F;
        y1 = x1 + h * (-3) * (x1 - x2);
        y2 = x2 + h * (-x1 * x3 + c * x1 - x2);
        y3 = x3 + h * (x1 * x2 - x3);

        u1 = width / 2 * (1 + x1 / 15);
        u2 = height / 2 * (1 + x2 / 25);
        v1 = width / 2 * (1 + y1 / 15);
        v2 = height / 2 * (1 + y2 / 25);

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHints(rh);
        g2.setColor(Color.getHSBColor(hue, 1.F, 1.F));
        g2.drawLine((int) u1, (int) u2, (int) v1, (int) v2);

        x1 = y1;
        x2 = y2;
        x3 = y3;
    }

    public void run() {
        Graphics g = getGraphics();
        while (true) {
            paint(g);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {

            }
        }
    }
}
























