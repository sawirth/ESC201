package parallel;


public class Barrier {

    private int nThreads;
    private int nWaiting;

    public Barrier() {
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

    public void release() {
        nThreads--;
    }

    public void acquire() {
        nThreads++;
    }
}
