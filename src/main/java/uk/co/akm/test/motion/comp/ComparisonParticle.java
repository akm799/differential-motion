package uk.co.akm.test.motion.comp;

import uk.co.akm.test.motion.particle.Particle;
import uk.co.akm.test.motion.particle.State;

/**
 * Created by Thanos Mavroidis on 09/01/2019.
 */
public abstract class ComparisonParticle extends Particle {

    public ComparisonParticle(double vx0, double vy0, double vz0, double x0, double y0, double z0, boolean quadSpace) {
        super(vx0, vy0, vz0, x0, y0, z0, quadSpace);
    }

    protected abstract State eExactState(double t);

    public final void setComparator(StateComparator comparator) {
        setStateMonitor(new ComparisonStateMonitor(this, comparator));
    }

    public final void removeComparator() {
        removeStateMonitor();
    }
}


