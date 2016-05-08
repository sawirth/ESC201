package sor;

import parallel.Barrier;

import java.util.ArrayList;
import java.util.List;


public class SOR {

    public double omega;
    public Barrier barrier;
    public double u[][];
    public double R[][];
    public int J;
    public int L;
    private static final int NUM_THREADS = 4;


    public SOR(int J, int L, double omega) {
        this.J = J;
        this.L = L;
        this.omega = omega;
        barrier = new Barrier();

        initializeArrays();
        calculate();
    }

    private void initializeArrays() {
        u = new double[J][L];
        R = new double[J][L];

        int middleL = L / 2;
        int beginPlate = J / 4;
        int endPlate = (int) (J / 4.0 * 3);

        for (int l = 0; l < L; l++) {
            for (int j = 0; j < J; j++) {
                if (l == 0 || j == 0 || l == L - 1 || j == J - 1) {
                    //Rand
                    u[j][l] = 0;
                    R[j][l] = 0;
                } else if (l == middleL && j >= beginPlate && j <= endPlate) {
                    //Metallplatte
                    u[j][l] = 1000;
                    R[j][l] = 0;
                } else {
                    //Restliche felder
                    u[j][l] = 0;
                    R[j][l] = omega / 4.0;
                }
            }
        }

        //Zweite Metallplatte
        for (int l = 150; l < 700; l++) {
            u[150][l] = 1000;
            R[150][l] = 0;
        }
    }


    private void calculate() {

        int[] rows = {1, 249, 499, 749, 998};
//        int[] rows = {1, 449, 998};
//        int[] rows = {1, 125, 250, 375, 500, 625, 750, 875, 998};


        List<SorThread> threads = new ArrayList<>();

        for (int i = 0; i < NUM_THREADS; i++) {
            System.out.println("Create Thread " + i);
            SorThread sorThread = new SorThread(i, rows[i], rows[i + 1], barrier, this);
            sorThread.start();
            threads.add(sorThread);
        }

        for (SorThread thread : threads) {
            if (thread.isAlive()) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Thread " + thread.id  + " joined");
        }
    }


    public double[][] getU() {
        return u;
    }
}

