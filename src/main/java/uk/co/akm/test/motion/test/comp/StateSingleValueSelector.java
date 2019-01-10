package uk.co.akm.test.motion.test.comp;

import uk.co.akm.test.motion.particle.State;

/**
 * Created by Thanos Mavroidis on 09/01/2019.
 */
public interface StateSingleValueSelector {

    double value(State state);
}
