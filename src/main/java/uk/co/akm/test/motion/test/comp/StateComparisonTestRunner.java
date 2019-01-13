package uk.co.akm.test.motion.test.comp;

import uk.co.akm.test.motion.particle.MotionIntegrator;
import uk.co.akm.test.motion.particle.Particle;

/**
 * Created by Thanos Mavroidis on 09/01/2019.
 */
public class StateComparisonTestRunner {
    private final StateComparisonTest[] tests;

    public StateComparisonTestRunner(StateComparisonTest test) {
        this(new StateComparisonTest[]{test});
    }

    public StateComparisonTestRunner(StateComparisonTest[] tests) {
        this.tests = tests;
    }

    public final void runComparisonTests(int n, double t) {
        final Particle[] particles = initialStates();
        MotionIntegrator.integrateMotion(particles, t, n);
    }

    private Particle[] initialStates() {
        final Particle[] particles = new Particle[tests.length];
        for (int i=0 ; i<tests.length ; i++) {
            particles[i] = tests[i].initialState();
        }

        return particles;
    }
}
