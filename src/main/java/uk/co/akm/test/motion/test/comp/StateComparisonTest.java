package uk.co.akm.test.motion.test.comp;

import uk.co.akm.test.motion.comp.ComparisonParticle;

/**
 * Created by Thanos Mavroidis on 09/01/2019.
 */
public interface StateComparisonTest<T> {

    String title();

    ComparisonParticle initialState();

    StateComparisonResult<T> result();
}
