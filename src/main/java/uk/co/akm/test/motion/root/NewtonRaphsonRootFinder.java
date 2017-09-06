package uk.co.akm.test.motion.root;

final class NewtonRaphsonRootFinder {
    private static final int MAX_STEPS_DEFAULT = 100;

    private final int maxSteps;

    NewtonRaphsonRootFinder() {
        this(MAX_STEPS_DEFAULT);
    }

    NewtonRaphsonRootFinder(int maxSteps) {
        if (maxSteps <= 0) {
            throw new IllegalArgumentException("Illegal 'maxSteps' argument: " + maxSteps + ". 'maxSteps' must be positive.");
        }

        this.maxSteps = maxSteps;
    }

    double findRoot(Function function, Function derivative, double rootApproximation, double minImprovement) {
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
            throw new ArithmeticException("Failed to converge with the required minimum improvement " + minImprovement + " after " + maxSteps + " steps. Last result: " + xn);
        }

        return xn;
    }
}
