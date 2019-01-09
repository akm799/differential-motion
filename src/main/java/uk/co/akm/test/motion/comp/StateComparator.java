package uk.co.akm.test.motion.comp;

import uk.co.akm.test.motion.particle.State;

/**
 * Created by Thanos Mavroidis on 09/01/2019.
 */
public interface StateComparator {

    void stateUpdated(State exact, State approximate);
}
