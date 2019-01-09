package uk.co.akm.test.motion.comp;

import uk.co.akm.test.motion.particle.State;
import uk.co.akm.test.motion.particle.StateMonitor;

/**
 * Created by Thanos Mavroidis on 09/01/2019.
 */
final class ComparisonStateMonitor implements StateMonitor {
    private final StateComparator comparator;
    private final ComparisonParticle comparisonParticle;

    ComparisonStateMonitor(ComparisonParticle comparisonParticle, StateComparator comparator) {
        this.comparisonParticle = comparisonParticle;
        this.comparator = comparator;
    }

    @Override
    public void stateUpdated(State state) {
        final State exact = comparisonParticle.eExactState(state.t());
        checkExactStateTime(exact.t(), state.t());
        comparator.stateUpdated(exact, state);
    }

    private void checkExactStateTime(double exactStateTime, double updatedStateTime) {
        if (exactStateTime != updatedStateTime) {
            throw new IllegalStateException("Exact state time (" + exactStateTime + ") is not exactly the same as the updated state time (" + updatedStateTime + "). No comparison can be made unless the exact state is evaluated at exactly the same time.");
        }
    }
}
