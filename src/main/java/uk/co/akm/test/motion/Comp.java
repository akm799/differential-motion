package uk.co.akm.test.motion;

import uk.co.akm.test.motion.particle.State;
import uk.co.akm.test.motion.test.comp.StateComparisonTest;
import uk.co.akm.test.motion.test.comp.StateComparisonTestRunner;
import uk.co.akm.test.motion.test.comp.StateSingleValueSelector;
import uk.co.akm.test.motion.test.comp.examples.FreeFallComparisonTest;

/**
 * Created by Thanos Mavroidis on 10/01/2019.
 */
public class Comp {

    public static void main(String[] args) {
        final Comp comp = new Comp();
        comp.runAllTests();
    }

    private Comp() {}

    private void runAllTests() {
        runFreeFallTests();
    }

    private void runFreeFallTests() {
        final StateComparisonTest<Double> freeFallSpaceLinTest = buildFreeFallSpaceTest(false);
        final StateComparisonTest<Double> freeFallVelocityLinTest = buildFreeFallVelocityTest(false);

        final StateComparisonTest<Double> freeFallSpaceQuadTest = buildFreeFallSpaceTest(true);
        final StateComparisonTest<Double> freeFallVelocityQuadTest = buildFreeFallVelocityTest(true);

        final double duration = 10;
        final int[] numberOfSteps = {10, 100, 1000, 10000};

        System.out.println("Free fall comparison tests");
        System.out.println("\n");

        System.out.println("Linear space-change approximation:");
        runTest(numberOfSteps, duration, freeFallSpaceLinTest);
        System.out.println("");

        System.out.println("Quadratic space-change approximation:");
        runTest(numberOfSteps, duration, freeFallSpaceQuadTest);
        System.out.println("\n");

        System.out.println("Linear velocity-change approximation:");
        runTest(numberOfSteps, duration, freeFallVelocityLinTest);
        System.out.println("");

        System.out.println("Quadratic velocity-change approximation:");
        runTest(numberOfSteps, duration, freeFallVelocityQuadTest);
    }

    private StateComparisonTest<Double> buildFreeFallSpaceTest(boolean quadSpace) {
        final StateSingleValueSelector ySelector = (State state) -> state.y();
        return new FreeFallComparisonTest(1000, quadSpace, "Free falling particle displacement", "Average y-coordinate difference", ySelector);
    }

    private StateComparisonTest<Double> buildFreeFallVelocityTest(boolean quadSpace) {
        final StateSingleValueSelector ySelector = (State state) -> state.vy();
        return new FreeFallComparisonTest(1000, quadSpace, "Free falling particle velocity", "Average y-velocity difference", ySelector);
    }

    private void runTest(int[] numberOfSteps, double duration, StateComparisonTest test) {
        System.out.println(test.title() + " - " + test.resultDescription() + ":");
        for (int n: numberOfSteps) {
            runTest(n, duration, test);
        }
    }

    private void runTest(int numberOfSteps, double duration, StateComparisonTest test) {
        final StateComparisonTestRunner testRunner = new StateComparisonTestRunner(test);
        testRunner.runComparisonTests(numberOfSteps, duration);
        System.out.println("dt=" + (duration/numberOfSteps) + " result=" + test.result().getComparisonResult());
    }
}
