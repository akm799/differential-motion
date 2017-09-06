package uk.co.akm.test.motion.root;

public final class RootFinder {
    private final MidPointRootFinder midPointRootFinder = new MidPointRootFinder();
    private final NewtonRaphsonRootFinder newtonRaphsonRootFinder = new NewtonRaphsonRootFinder();

    public double findRoot(Function function, Function derivative, double xMin, double xMax, double minImprovement) {
        final double x0 = midPointRootFinder.findRoot(function, xMin, xMax, 10*minImprovement);

        return newtonRaphsonRootFinder.findRoot(function, derivative, x0, minImprovement);
    }

    //TODO Move to unit tests.
    public static void main(String[] args) {
        sinTest();
        lnTest();
    }

    private static void sinTest() {
        final Function sin = (x -> Math.sin(x));
        final Function cos = (x -> Math.cos(x));
        final double xMin = 3*Math.PI/4;
        final double xMax = 3*Math.PI/2;
        final double minImprovement = 0.00001;
        final double exact = Math.PI;

        test("sin(x)", sin, cos, xMin, xMax, minImprovement, exact);
    }

    private static void lnTest() {
        final Function ln = (x -> Math.log(x));
        final Function inv = (x -> 1/x);
        final double xMin = 0.001;
        final double xMax = 20;
        final double minImprovement = 0.00001;
        final double exact = 1;

        test("ln(x)", ln, inv, xMin, xMax, minImprovement, exact);
    }

    private static void test(String title, Function function, Function derivative, double xMin, double xMax, double minImprovement, double exact) {
        final RootFinder underTest = new RootFinder();
        final double approx = underTest.findRoot(function, derivative, xMin, xMax, minImprovement);
        System.out.println(title + ":  exact=" + exact + " approx=" + approx + " error-fraction=" + Math.abs((exact - approx))/exact);
    }
}
