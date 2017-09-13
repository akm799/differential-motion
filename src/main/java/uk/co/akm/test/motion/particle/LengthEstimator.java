package uk.co.akm.test.motion.particle;

/**
 * Utility class to calculate the parabolic arc segment length.
 *
 * http://planetmath.org/arclengthofparabola
 *
 * Created by Thanos Mavroidis on 13/09/2017.
 */
public class LengthEstimator {

    /**
     * Returns the length of the parabolic arc segment of the parabola a*x^2
     * from the point with x-coordinate 0 to the point with x-coordinate x.
     *
     * @param a the coefficient of the x^2 (single) term of the parabola
     * @param x the x-coordinate at the end of the arc segment
     * @return the length of the parabolic arc segment of the parabola a*x^2
     * from the point with x-coordinate 0 to the point with x-coordinate x
     */
    public static double parabolicArcLength(double a, double x) {
        final double tax = 2*a*x;
        return (tax*Math.sqrt(1 + tax*tax) + arsinh(tax))/(4*a);
    }

    private static double arsinh(double x) {
        return Math.log(x + Math.sqrt(1 + x*x));
    }

    private LengthEstimator() {}
}
