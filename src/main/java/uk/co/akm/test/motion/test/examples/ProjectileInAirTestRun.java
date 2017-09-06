package uk.co.akm.test.motion.test.examples;

import uk.co.akm.test.motion.root.Function;
import uk.co.akm.test.motion.root.RootFinder;

public final class ProjectileInAirTestRun {
    private final double g = 9.81;
    private final double k = 0.0654; // Should give terminal velocity of 150 m/s.
    private final double v0 = 100;
    private final double theta = Math.PI/4; // 45 degree launch angle.
    private final double vx0 = v0*Math.cos(theta);
    private final double vy0 = v0*Math.sin(theta);

    private double evaluateRange() {
        final double gok = g/k;

        final double tUp = -Math.log(gok/(vy0 + gok))/k; // Time of flight from zero to maximum height.
        final double tDown = evaluateDescentTime(tUp); // Time of flight from maximum height back to zero.
        final double tof = (tUp + tDown);

        return (vx0/k)*(1 - Math.exp(-k*tof));
    }

    /**
     * Compute the time of flight from maximum height back to zero. We cannot use a formula because we need to solve
     * a transcendental equation, which needs to be solved numerically.
     */
    private double evaluateDescentTime(double ascendTime) {
        final double tMin = 0;
        final double tMax = 3*ascendTime;
        final Function function = new Y(g, k, vy0, ascendTime);
        final Function derivative = new VY(g, k);
        final RootFinder rootFinder = new RootFinder();

        return rootFinder.findRoot(function, derivative, tMin, tMax, 0.0001); // 10th of a millisecond accuracy.
    }

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

    public static void main(String[] args) {
        final ProjectileInAirTestRun test = new ProjectileInAirTestRun();
        final double range = test.evaluateRange();
        System.out.println("range=" + range);
    }
}

/*

y-force = gravity + air-resistance
                         g    g
vy(t) = exp(-kt) * (v0 + -) - -
                         k    k

            1          g                     g
y(t) = y0 + - * (vy0 + -) * (1 - exp(-kt)) - - * t
            k          k                     k

x-force = air-resistance

            vx0
x(t) = x0 + --- * (1 - exp(-kt))
             k

Initial conditions: x0 = 0, y0 = 0. vx0 = v0*cos(theta), vy0 = v0*sin(theta)
*/

