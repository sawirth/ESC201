package house3d;

import javax.swing.*;
import java.awt.*;

public class House3DExample extends JFrame {

    private House3D house;

    public House3DExample() {
        initUI();
    }

    private void initUI() {
        this.house = new House3D();
        add(house);
        setTitle("3D House Rotation");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        repaint();
        run();
    }

    public void run() {
        double angle = Math.PI * 2 / 100;
        while(true) {
            house.rotateX(angle);
            angle += Math.PI * 2 / 100;
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                House3DExample ex = new House3DExample();
                ex.setVisible(true);
            }
        });
    }
}
