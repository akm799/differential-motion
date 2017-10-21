package uk.co.akm.test.motion.test.examples;

import uk.co.akm.test.motion.particle.Particle;
import uk.co.akm.test.motion.particle.examples.ParticleWithMassInAir;
import uk.co.akm.test.motion.root.Function;
import uk.co.akm.test.motion.root.RootFinder;
import uk.co.akm.test.motion.test.AbstractMultiParticleTestRun;
import uk.co.akm.test.motion.test.result.Result;
import uk.co.akm.test.motion.test.result.impl.DoubleResult;

/**
 * Particle race between two particles propelled by the same force for the same amount of time.
 * The first particle moves in a vacuum and, thus, experiences no other force. The second particle
 * moves inside a gas and, thus, experiences an air-resistance against its motion which proportional
 * to its speed. The two particles have different masses. The mass difference is such that they will
 * both cover the same distance at the same (race) time.
 *
 * Created by Thanos Mavroidis on 21/10/2017.
 */
public class ParticleRaceTestRun extends AbstractMultiParticleTestRun {
    private static final int PARTICLE_ONE_DISTANCE_INDEX = 0;
    private static final int PARTICLE_TWO_DISTANCE_INDEX = 1;
    private static final int PARTICLE_DIFF_DISTANCE_INDEX = 2;

    private final double x = 2*Math.PI; // Distance covered in the race.

    private final double f = 1; // Propulsion force for both particles.

    private final double vAirMax = x/0.5; // Terminal velocity attained by the air-resistance experiencing particle.
    private final double k = f/vAirMax; // Resistance coefficient for particle experiencing air-resistance.

    private final double mFree = 1; // Mass of particle experiencing no resistance.

    private final double t; // Race time.
    private final double mAir; // Mass of particle experiencing air-resistance.

    public ParticleRaceTestRun() {
        super(
                new int[]{10, 100, 1000, 10000, 100000, 1000000, 10000000},
                new String[]{"x-free", "~x-free", "x-free error fraction", "x-air", "~x-air", "~x-air error fraction", "difference", "~difference", "difference error fraction"}
        );

        final double a1 = f/mFree;
        t = Math.sqrt(2*x/a1);
        mAir = computeMAir();
    }

    @Override
    public Result exactResult(int index) {
        switch (index) {
            case PARTICLE_ONE_DISTANCE_INDEX:
            case PARTICLE_TWO_DISTANCE_INDEX:
                return new DoubleResult(x);

            case PARTICLE_DIFF_DISTANCE_INDEX: return new DoubleResult(0);

            default: throw new IllegalArgumentException("Illegal result index: " + index);
        }
    }

    @Override
    public Result approxResult(int index) {
        switch (index) {
            case PARTICLE_ONE_DISTANCE_INDEX: return new DoubleResult(particles[PARTICLE_ONE_DISTANCE_INDEX].x());

            case PARTICLE_TWO_DISTANCE_INDEX: return new DoubleResult(particles[PARTICLE_TWO_DISTANCE_INDEX].x());

            case PARTICLE_DIFF_DISTANCE_INDEX: return new DoubleResult(Math.abs(particles[PARTICLE_ONE_DISTANCE_INDEX].x() - particles[PARTICLE_TWO_DISTANCE_INDEX].x()));

            default: throw new IllegalArgumentException("Illegal result index: " + index);
        }
    }

    @Override
    protected double duration() {
        return t;
    }

    @Override
    protected Particle[] initialStates() {
        return new Particle[]{
                new ParticleWithMassInAir(mFree, f, 0),
                new ParticleWithMassInAir(mAir, f, k)
        };
    }

    // Compute the mass of the particle experiencing air-resistance so that it will cover the same distance as the
    // free-moving particle at the same (race) time.
    private double computeMAir() {
        final RootFinder rootFinder = new RootFinder();
        final Function fn = new FixedDisplacement();
        final Function fnp = new FixedDisplacementDerivative();

        final double cMin = k/mFree;
        final double cMax = k/(0.1*mFree); // Mass underestimation by a factor of 10 (compared to the free-particle mass).
        final double c = rootFinder.findRoot(fn, fnp, cMin, cMax, 0.000000001);

        return k/c;
    }

    private class FixedDisplacement implements Function {
        private final double fok = f/k;
        private final double displacement = x;

        @Override
        public double f(double x) {
            return fok*(t + (Math.exp(-x*t) - 1)/x) - displacement;
        }
    }

    private class FixedDisplacementDerivative implements Function {
        private final double fok = f/k;

        @Override
        public double f(double x) {
            final double xsqInv = 1/(x*x);

            return fok*(xsqInv - Math.exp(-x*t)*(1 + xsqInv));
        }
    }
}
