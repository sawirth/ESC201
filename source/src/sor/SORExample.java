package sor;


import com.sun.corba.se.impl.orbutil.graph.Graph;

import javax.swing.*;
import java.awt.*;

public class SORExample extends JFrame {

    public SORExample() {
        initUI();
    }

    private void initUI() {
        //Default values
        int J = 100;
        int L = 100;
        double omega = 2 / (1 + Math.PI / J);

        setTitle("SOR Example");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        add(new SOR(J, L, omega));

        repaint();
    }


    public static void main(String args[]) {
        new SORExample();
    }
}
