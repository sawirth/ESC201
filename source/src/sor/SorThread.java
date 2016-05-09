package sor;

import parallel.Barrier;

public class SorThread extends Thread{

    private int minRow;
    private int maxRow;
    public int id;
    private Barrier barrier;
    private SOR sor;

    public SorThread(int id, int minRow, int maxRow, Barrier barrier, SOR sor) {
        this.minRow = minRow;
        this.maxRow = maxRow;
        this.barrier = barrier;
        this.sor = sor;
        this.id = id;
    }

    @Override
    public void run() {
        int counter = 0;
        System.out.println("Thread " + this.id + " -- Start Calculating...");

        barrier.acquire();
        do {
            double maxDelta = 0;
            double delta;
            for (int l = minRow; l <= maxRow; l++) {
                for (int j = 1 + (l % 2); j < this.sor.J - 1; j += 2) {
                    delta = calculateDelta(j, l);
                    this.sor.u[j][l] = this.sor.u[j][l] + delta;
                    maxDelta = Math.max(maxDelta, delta);
                }
            }

            barrier.reached();

            for (int l = minRow; l <= maxRow; l++) {
                for (int j = 1 + ((1 + l) % 2); j < this.sor.J - 1; j += 2) {
                    delta = calculateDelta(j, l);
                    this.sor.u[j][l] += delta;
                    maxDelta = Math.max(maxDelta, delta);
                }
            }
            barrier.reached();
            counter++;
            this.sor.maxDelta = Math.max(maxDelta, this.sor.maxDelta);
        } while (this.sor.maxDelta > 1);
        System.out.println("Thread " + this.id + " -- Finished Calculations: Iterations: " + counter);
        barrier.release();
    }


    private double calculateDelta(int j, int l) {
        return sor.R[j][l] * (sor.u[j + 1][l] + sor.u[j - 1][l] + sor.u[j][l + 1] + sor.u[j][l - 1] - 4 * sor.u[j][l]);
    }
}
