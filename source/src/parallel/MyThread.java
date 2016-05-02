package parallel;


public class MyThread extends Thread {

    private Barrier barrier;

    public MyThread(Barrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        super.run();

        //hier kommt die Berechnung hin
    }

    public static void main(String args[]) {

        final int N = 2;
        MyThread[] t = new MyThread[N];
        Barrier barrier = new Barrier(N);

        //Erstellen und starten
        for (int i = 0; i < N; i++) {
            t[i] = new MyThread(barrier);
            t[i].start();
        }

        //Warten bis alle Threads beendet sind
        for (int i = 0; i < N; i++) {
            if (t[i].isAlive()) {
                try {
                    t[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

