package differential;


public class HarmonianRK implements IFunction{

    @Override
    public double[] eval(double [] y) {

        double[] value = new double[2];
        double p = value[0];
        value[0] = -value[1];
        value[1] = p;

        return value;
    }
}
