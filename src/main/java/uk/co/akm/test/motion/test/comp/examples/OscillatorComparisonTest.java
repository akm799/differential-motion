package uk.co.akm.test.motion.test.comp.examples;

import uk.co.akm.test.motion.comp.ComparisonParticle;
import uk.co.akm.test.motion.comp.MutableState;
import uk.co.akm.test.motion.particle.State;
import uk.co.akm.test.motion.test.comp.AverageDifference;
import uk.co.akm.test.motion.test.comp.StateComparisonResult;
import uk.co.akm.test.motion.test.comp.StateComparisonTest;
import uk.co.akm.test.motion.test.comp.StateSingleValueSelector;

/**
 * Oscillator executing a simple harmonic motion.
 *
 * https://en.wikipedia.org/wiki/Simple_harmonic_motion
 *
 * Created by Thanos Mavroidis on 13/01/2019.
 */
public final class OscillatorComparisonTest implements StateComparisonTest<Double> {
    private final double k;
    private final double x0;
    private final boolean quadSpace;

    private final String title;
    private final String resultDescription;
    private final AverageDifference difference;

    public OscillatorComparisonTest(double k, double x0, boolean quadSpace, String title, String resultDescription, StateSingleValueSelector valueSelector) {
        this.k = k;
        this.x0 = x0;
        this.quadSpace = quadSpace;

        this.title = title;
        this.resultDescription = resultDescription;
        this.difference = new AverageDifference(valueSelector);
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String resultDescription() {
        return resultDescription;
    }

    @Override
    public ComparisonParticle initialState() {
        final ComparisonParticle particle = new Oscillator(k, 0, x0, quadSpace);
        particle.setComparator(difference);

        return particle;
    }

    @Override
    public StateComparisonResult<Double> result() {
        return difference;
    }

    private static final class Oscillator extends ComparisonParticle {
        private final double phi;
        private final double alpha;
        private final double omega;
        private final double minusKOverM;
        private final double minusAlphaTimesOmega;
        private final MutableState exactState = new MutableState();

        private final double m = 1; // Oscillator mass is 1 kg.

        Oscillator(double k, double vx0, double x0, boolean quadSpace) {
            super(0, 0, 0, x0, 0, 0, quadSpace);

            if (k <= 0) {
                throw new IllegalArgumentException("Illegal restoring force constant k=" + k + ". Negative or zero values are not allowed.");
            }

            this.omega = Math.sqrt(k/m);
            this.phi = Math.atan(vx0/(omega*x0));
            this.alpha = x0/Math.cos(phi);

            this.minusKOverM = -k/m;
            this.minusAlphaTimesOmega = -alpha*omega;
        }

        @Override
        protected State eExactState(double t) { // https://en.wikipedia.org/wiki/Simple_harmonic_motion
            final double a = omega*t - phi;
            exactState.setVx(minusAlphaTimesOmega*Math.sin(a));
            exactState.setX(alpha*Math.cos(a));
            exactState.setT(t);

            return exactState;
        }

        @Override
        protected void updateAcceleration(double dt) {
            this.ax = minusKOverM*x();
        }
    }
}
