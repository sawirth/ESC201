package house3d;

import javax.swing.*;
import java.awt.*;
import java.text.Format;
import java.text.NumberFormat;

public class House3DExample extends JFrame  {

    private House3D house;
    private JButton updateButton;
    private JCheckBox xCheckbox;
    private JCheckBox yCheckbox;
    private JCheckBox zCheckbox;

    private JFormattedTextField xAngleTextField;
    private JFormattedTextField yAngleTextField;
    private JFormattedTextField zAngleTextField;

    private JFormattedTextField msTextField;

    private double xAngle;
    private double yAngle;
    private double zAngle;
    private long ms;

    private double ds;
    private double d0;
    private int offset;
    private int scale;


    public House3DExample() {
        initUI();
    }

    private void initUI() {
        //Default values
        ds = 8;
        d0 = 10;
        offset = 390;
        scale = 270;

        //Initialize fields
        xAngle = 1;
        yAngle = 1;
        zAngle = 1;
        ms = 10;

        //Frame settings
        setTitle("3D House Rotation");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Layout
        getContentPane().setLayout(new BorderLayout());
        JPanel settingsPanel = new JPanel();

        JLabel label = new JLabel("Check the axis for rotation");
        settingsPanel.add(label);

        Format format = NumberFormat.getNumberInstance();

        //X axis
        xCheckbox = new JCheckBox("Rotate X");
        xAngleTextField = new JFormattedTextField(format);
        xAngleTextField.setColumns(2);
        xAngleTextField.setValue(xAngle);
        settingsPanel.add(xCheckbox);
        settingsPanel.add(xAngleTextField);

        //Y axis
        yCheckbox = new JCheckBox("Rotate Y");
        yAngleTextField = new JFormattedTextField(format);
        yAngleTextField.setColumns(2);
        yAngleTextField.setValue(yAngle);
        settingsPanel.add(yCheckbox);
        settingsPanel.add(yAngleTextField);

        //Z axis
        zCheckbox = new JCheckBox("Rotate Z");
        zAngleTextField = new JFormattedTextField(format);
        zAngleTextField.setColumns(2);
        zAngleTextField.setValue(zAngle);
        settingsPanel.add(zCheckbox);
        settingsPanel.add(zAngleTextField);

        //Label
        JLabel msLabel = new JLabel("Refresh rate");
        msTextField = new JFormattedTextField(format);
        msTextField.setColumns(3);
        msTextField.setValue(ms);
        settingsPanel.add(msLabel);
        settingsPanel.add(msTextField);

        //Update Button
        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updateValues());
        settingsPanel.add(updateButton);

        //Add the panel to the top of the window
        add(settingsPanel, BorderLayout.NORTH);

        //Add the house
        this.house = new House3D(ds, d0, offset, scale);
        add(house, BorderLayout.CENTER);

        //Activate
        setVisible(true);
        repaint();
        run();
    }

    public void run() {
        while (isVisible()) {
            if (this.xCheckbox.isSelected()) {
                house.rotateX(xAngle);
            }

            if (this.yCheckbox.isSelected()) {
                house.rotateY(yAngle);
            }

            if (this.zCheckbox.isSelected()) {
                house.rotateZ(zAngle);
            }

            repaint();
            try {
                Thread.sleep(ms);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    public void updateValues() {
        try {
            xAngle = Double.parseDouble(xAngleTextField.getText());
            yAngle = Double.parseDouble(yAngleTextField.getText());
            zAngle = Double.parseDouble(zAngleTextField.getText());
            ms = Long.parseLong(msTextField.getText());
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static void main(String args[]) {
        new House3DExample();
    }

}
