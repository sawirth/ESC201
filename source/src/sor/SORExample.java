package sor;


import javax.swing.*;

public class SORExample extends JFrame {

    public SORExample() {
        initUI();
    }

    private void initUI() {
        //Default values
        int J = 1000;
        int L = 1000;
        double omega = 2 / (1 + Math.PI / J);

        setTitle("SOR Example");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        SOR sor = new SOR(J, L, omega);
        SOR_Panel sor_panel = new SOR_Panel(sor.getU(), L, J);
        add(sor_panel);
        sor_panel.repaint();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public static void main(String args[]) {
        new SORExample();
    }
}
