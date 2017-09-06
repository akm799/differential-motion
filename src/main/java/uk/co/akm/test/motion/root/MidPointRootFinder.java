package uk.co.akm.test.motion.root;

final class MidPointRootFinder {
    private static final int MAX_STEPS_DEFAULT = 10000;

    private final int maxSteps;

    MidPointRootFinder() {
        this(MAX_STEPS_DEFAULT);
    }

    MidPointRootFinder(int maxSteps) {
        if (maxSteps <= 0) {
            throw new IllegalArgumentException("Illegal 'maxSteps' argument: " + maxSteps + ". 'maxSteps' must be positive.");
        }

        this.maxSteps = maxSteps;
    }

    double findRoot(Function function, double xMin, double xMax, double minRange) {
        checkRange(function, xMin, xMax);

        int nSteps = 0;
        double diff = Double.MAX_VALUE;
        while (diff > minRange && nSteps <= maxSteps) {
            final double xMid = xMin + (xMax - xMin)/2;
            final boolean left = rangeContainsRoot(function, xMin, xMid);
            final boolean right = rangeContainsRoot(function, xMid, xMax);

            checkNewRanges(left, right);

            if (left) {
                xMax = xMid;
            } else {
                xMin = xMid;
            }

            diff = xMax - xMin;
            nSteps++;
        }

        if (nSteps > maxSteps) {
            throw new ArithmeticException("Failed to converge with the required minimum range " + minRange + " after " + maxSteps + " steps.");
        }

        return (xMin + (xMax - xMin)/2);
    }

    private void checkRange(Function function, double xMin, double xMax) {
        if (xMin >= xMax) {
            throw new ArithmeticException("Invalid range [" + xMin + ", " + xMax + "]. The minimum must ge less than the maximum.");
        }

        if (!rangeContainsRoot(function, xMin, xMax)) {
            throw new ArithmeticException("Invalid range [" + xMin + ", " + xMax + "]. The range does not contain a function x-intercept.");
        }
    }

    private boolean rangeContainsRoot(Function function, double xMin, double xMax) {
        return (function.f(xMin)*function.f(xMax) < 0);
    }

    private void checkNewRanges(boolean left, boolean right) {
        if (left && right) {
            throw new ArithmeticException("Range sub-division error: Both the LHS and RHS sub-range may contain a function x-intercept.");
        }

        if (!left && !right) {
            throw new ArithmeticException("Range sub-division error: Neither the LHS nor RHS sub-range contain a function x-intercept.");
        }
    }
}
