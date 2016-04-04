package differential;

public class LotkaVoltera implements IFunction {
    double ke=2.;
    double kea=0.02;
    double kae=0.01;
    double ka=1.06;

    public double[] eval(double [] y){
        double [] dy = new double[2];
        dy[0] = ke * y[0] - kea * y[0] * y[1];
        dy[1] = -ka * y[1] + kae * y[0] * y[1];
        return dy;
    }
}
