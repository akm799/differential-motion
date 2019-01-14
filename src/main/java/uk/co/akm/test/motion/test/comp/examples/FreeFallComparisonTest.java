package uk.co.akm.test.motion.test.comp.examples;

import uk.co.akm.test.motion.comp.ComparisonParticle;
import uk.co.akm.test.motion.comp.MutableState;
import uk.co.akm.test.motion.particle.State;
import uk.co.akm.test.motion.test.comp.*;

/**
 * Created by Thanos Mavroidis on 09/01/2019.
 */
public final class FreeFallComparisonTest extends AbstractSingleValueDifferenceTest {
    private final double y0;

    public FreeFallComparisonTest(double y0, boolean quadSpace, String title, String resultDescription, StateSingleValueSelector valueSelector) {
        super(quadSpace, title, resultDescription, valueSelector);

        this.y0 = y0;
    }

    @Override
    protected final ComparisonParticle buildComparisonParticle(boolean quadSpace) {
        return new FreeFallingParticle(y0, quadSpace);
    }

    private static final class FreeFallingParticle extends ComparisonParticle {
        private final double y0;
        private final double ayOver2;
        private final MutableState exactState = new MutableState();

        FreeFallingParticle(double y0, boolean quadSpace) {
            super(0, 0, 0, 0, y0, 0, quadSpace);

            this.ay = -9.81; // Free fall.
            this.y0 = y0;
            this.ayOver2 = this.ay/2;
        }

        @Override
        protected State eExactState(double t) {
            exactState.setVy(ay*t);
            exactState.setY(y0 + ayOver2*t*t);
            exactState.setT(t);

            return exactState;
        }

        @Override
        protected void updateAcceleration(double dt) {}
    }
}
