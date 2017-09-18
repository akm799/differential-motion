package uk.co.akm.test.motion.root;

public final class RootFinder {
    private static final double MIN_RANGE_TO_INIT_RANGE_FACTOR = 0.01;
    private static final int MIN_RANGE_TO_MIN_IMPROVEMENT_FACTOR = 10;

    private final MidPointRootFinder midPointRootFinder = new MidPointRootFinder();
    private final NewtonRaphsonRootFinder newtonRaphsonRootFinder = new NewtonRaphsonRootFinder();

    public double findRoot(Function function, Function derivative, double xMin, double xMax, double minImprovement) {
        final double minRange = evaluateMidPointMethodMinRange(xMin, xMax, minImprovement);
        final double x0 = midPointRootFinder.findRoot(function, xMin, xMax, minRange);

        return newtonRaphsonRootFinder.findRoot(function, derivative, x0, minImprovement);
    }

    private double evaluateMidPointMethodMinRange(double xMin, double xMax, double minImprovement) {
        final double fromInitRange = (xMax - xMin)*MIN_RANGE_TO_INIT_RANGE_FACTOR;
        final double fromMinImprovement = MIN_RANGE_TO_MIN_IMPROVEMENT_FACTOR*minImprovement;

        return Math.min(fromInitRange, fromMinImprovement);
    }
}
