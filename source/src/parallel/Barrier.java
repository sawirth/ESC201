package parallel;


public class Barrier {

    private int nThreads;
    private int nWaiting;

    public Barrier(int nThreads) {
        this.nThreads = nThreads;
        nWaiting = 0;
    }

    public synchronized void reached() {
        nWaiting++;
        if (nThreads != nWaiting) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            notifyAll();
            nWaiting = 0;
        }
    }
}
