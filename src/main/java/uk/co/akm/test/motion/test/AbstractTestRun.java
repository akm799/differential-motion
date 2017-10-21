package uk.co.akm.test.motion.test;

import uk.co.akm.test.motion.particle.Particle;

/**
 * Created by Thanos Mavroidis on 03/09/2017.
 */
public abstract class AbstractTestRun extends AbstractMultiParticleTestRun {
    protected Particle particle;

    protected AbstractTestRun(int[] nSteps, String[] headers) {
        super(nSteps, headers);
    }

    @Override
    protected final Particle[] initialStates() {
        particle = initialState();
        return new Particle[]{particle};
    }

    protected abstract Particle initialState();
}
