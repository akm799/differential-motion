package uk.co.akm.test.motion.root;

public class NewtonRaphsonRootFinder {
    private static final int MAX_STEPS_DEFAULT = 100;

    private final int maxSteps;

    public NewtonRaphsonRootFinder() {
        this(MAX_STEPS_DEFAULT);
    }

    public NewtonRaphsonRootFinder(int maxSteps) {
        if (maxSteps <= 0) {
            throw new IllegalArgumentException("Illegal 'maxSteps' argument: " + maxSteps + ". 'maxSteps' must be positive.");
        }

        this.maxSteps = maxSteps;
    }

    public double findRoot(Function function, Function derivative, double rootApproximation, double minImprovement) {
        double xn = Double.MAX_VALUE;
        double xnm1 = rootApproximation;
        double diff = Double.MAX_VALUE;

        int nSteps = 0;
        while (diff > minImprovement && nSteps <= maxSteps) {
            final double f = function.f(xnm1);
            if (f == 0) {
                return xnm1; // We are so lucky!
            }

            final double fp = derivative.f(xnm1);
            if (fp == 0) {
                throw new ArithmeticException("Function derivative evaluated to zero at point " + xnm1 + ". No convergence appears possible."); // We are so unlucky.
            }

            xn = xnm1 - f/fp;
            diff = Math.abs(xn - xnm1);
            xnm1 = xn;
            nSteps++;
        }

        if (nSteps > maxSteps) {
            throw new ArithmeticException("Failed to converge with the required minimum improvement " + minImprovement + " after " + maxSteps + " steps.");
        }

        return xn;
    }

    //TODO Move to unit tests.
    public static void main(String[] args) {
        sinTest();
        lnTest();
    }

    private static void sinTest() {
        final Function sin = (x -> Math.sin(x));
        final Function cos = (x -> Math.cos(x));
        final double x0 = 3*Math.PI/4;
        final double minImprovement = 0.001;
        final double exact = Math.PI;

        test("sin(x)", sin, cos, x0, minImprovement, exact);
    }

    private static void lnTest() {
        final Function ln = (x -> Math.log(x));
        final Function inv = (x -> 1/x);
        final double x0 = 2;
        final double minImprovement = 0.0001;
        final double exact = 1;

        test("ln(x)", ln, inv, x0, minImprovement, exact);
    }

    private static void test(String title, Function function, Function derivative, double rootApproximation, double minImprovement, double exact) {
        final NewtonRaphsonRootFinder underTest = new NewtonRaphsonRootFinder();
        final double approx = underTest.findRoot(function, derivative, rootApproximation, minImprovement);
        System.out.println(title + ":  exact=" + exact + " approx=" + approx + " error-fraction=" + Math.abs((exact - approx))/exact);
    }
}
