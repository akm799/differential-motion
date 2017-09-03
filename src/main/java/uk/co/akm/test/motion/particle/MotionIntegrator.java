package uk.co.akm.test.motion.particle;

/**
 * Created by Thanos Mavroidis on 02/09/2017.
 */
public class MotionIntegrator {

    /**
     * Performs the basic numerical integration to update the final particle state.
     *
     * @param movingParticle the particle whose sate will be updated
     * @param timePeriod the total time over which to integrate
     * @param nSteps the number of integration steps
     */
    public static void integrateMotion(Particle movingParticle, double timePeriod, int nSteps) {
        final double dt = timePeriod/nSteps;
        for (int i=0 ; i<nSteps ; i++) {
            movingParticle.update(dt);
        }
    }

    private MotionIntegrator() {}
}
