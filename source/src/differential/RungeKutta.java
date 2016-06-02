package differential;


public class RungeKutta {

    public static double [] MidpointRK(IFunction f, double[] yn, double h){

        double[] k1 = f.eval(yn);
        for (int i = 0; i < k1.length; i++){
            k1[i] = yn[i] + 0.5 * h * k1[i];
        }

        double[] k2 = f.eval(k1);
        for (int i = 0; i < k2.length; i++){
            k2[i] = yn[i] + h * k2[i];
        }

        return k2;
    }
}
