package uk.co.akm.test.motion.test.examples;

import uk.co.akm.test.motion.root.Function;
import uk.co.akm.test.motion.root.RootFinder;
import uk.co.akm.test.motion.test.AbstractTestRun;
import uk.co.akm.test.motion.test.result.Result;
import uk.co.akm.test.motion.test.result.impl.PointResult;

/**
 * Created by Thanos Mavroidis on 10/09/2017.
 */
abstract class AbstractProjectileInAirTestRun extends AbstractTestRun {
    private final double v0 = 100;
    private final double theta = Math.PI/4; // 45 degree launch angle.

    final double g = 9.81;
    final double k = 0.0654; // Should give terminal velocity of 150 m/s.
    final double vx0 = v0*Math.cos(theta);
    final double vy0 = v0*Math.sin(theta);

    private final double gok = g/k;

    private final double oneTenthOfAMilliSec = 0.0001;
    private final RootFinder rootFinder = new RootFinder();

    private final double timeOfFlight;
    private PointResult target;

    protected AbstractProjectileInAirTestRun() {
        super(
                new int[]{10, 100, 1000, 10000, 100000, 1000000, 10000000},
                new String[]{"end-point", "~end-point", "end-point error fraction"}
        );

        this.timeOfFlight = evaluateTimeOfFlight();
    }

    private double evaluateTimeOfFlight() {
        final double tUp = -Math.log(gok/(vy0 + gok))/k; // Time of flight from zero to maximum height.
        final double tDown = evaluateDescentTime(tUp); // Time of flight from maximum height back to zero (needs to be evaluated numerically).

        return (tUp + tDown);
    }

    /**
     * Compute the time of flight from maximum height back to zero. We cannot use a formula because we need to numerically
     * solve a transcendental equation with no analytical solution.
     */
    private double evaluateDescentTime(double ascendTime) {
        final double tMin = 0;
        final double tMax = 3*ascendTime;
        final Function function = new Y(g, k, vy0, ascendTime);
        final Function derivative = new VY(g, k);

        return rootFinder.findRoot(function, derivative, tMin, tMax, oneTenthOfAMilliSec);
    }

    @Override
    protected final double duration() {
        return timeOfFlight;
    }

    @Override
    public final Result exactResult(int index) {
        if (target == null) {
            target = evaluateTarget(timeOfFlight); // The target cannot be initialized in the consructor since its evaluation it might require parameters available only in the sub-class.
        }

        return target;
    }

    protected abstract PointResult evaluateTarget(double timeOfFlight);

    @Override
    public final Result approxResult(int index) {
        return new PointResult(particle);
    }

    /**
     * The y(t) function of the descending projectile from y(0)=h to y(td)=0, where h is the maximum height reached and
     * td is the descend time. We need this because we are going to use it to obtain a numerical solution for td.
     */
    private static class Y implements Function {
        private final double k;

        private final double gok;
        private final double invk;

        private final double maxHeight;

        private Y(double g, double k, double vy0, double ascendTime) {
            this.k = k;
            this.gok = g/k;
            this.invk = 1/k;
            this.maxHeight = invk*(vy0 + gok)*(1 - Math.exp(-k*ascendTime)) - gok * ascendTime;
        }

        @Override
        public double f(double t) {
            return maxHeight + invk*gok*(1 - Math.exp(-k*t)) - gok * t;
        }
    }

    /**
     * The vy(t) function of the descending projectile from vy(0)=0 to vy(td)=v, where td is the descend time. This
     * function is the derivative of the y(t) function of the descending projectile from y(0)=h to y(td)=0, where h is
     * the maximum height reached. We need this derivative because we are going to use it to obtain a numerical solution
     * for y(td)=0 (for our numerical solution both the function y(t) and its derivative vy(t) are required).
     */
    private static class VY implements Function {
        private final double k;
        private final double gok;

        public VY(double g, double k) {
            this.k = k;
            this.gok = g/k;
        }

        @Override
        public double f(double t) {
            return Math.exp(-k*t)*gok - gok;
        }
    }
}

/*
y-force = gravity + air-resistance = -g -k*vy
                          g    g
vy(t) = exp(-kt) * (vy0 + -) - -
                          k    k

            1          g                     g
y(t) = y0 + - * (vy0 + -) * (1 - exp(-kt)) - - * t
            k          k                     k

Initial conditions: y0 = 0, vy0 = v0*sin(theta)
*/

