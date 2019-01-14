package uk.co.akm.test.motion;

import uk.co.akm.test.motion.particle.State;
import uk.co.akm.test.motion.test.comp.StateComparisonTest;
import uk.co.akm.test.motion.test.comp.StateComparisonTestRunner;
import uk.co.akm.test.motion.test.comp.StateSingleValueSelector;
import uk.co.akm.test.motion.test.comp.examples.FreeFallComparisonTest;
import uk.co.akm.test.motion.test.comp.examples.OscillatorComparisonTest;

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
        System.out.println("\n\n");
        runOscillatorTests();
    }

    private void runFreeFallTests() {
        final StateComparisonTest<Double> freeFallDisplacementLinTest = buildFreeFallDisplacementTest(false);
        final StateComparisonTest<Double> freeFallVelocityLinTest = buildFreeFallVelocityTest(false);

        final StateComparisonTest<Double> freeFallDisplacementQuadTest = buildFreeFallDisplacementTest(true);
        final StateComparisonTest<Double> freeFallVelocityQuadTest = buildFreeFallVelocityTest(true);

        final double duration = 10;
        final int[] numberOfSteps = {10, 100, 1000, 10000};

        System.out.println("Free fall comparison tests");
        System.out.println("--------------------------");
        System.out.println("\n");

        System.out.println("Linear displacement-change approximation:");
        runTest(numberOfSteps, duration, freeFallDisplacementLinTest);
        System.out.println("");

        System.out.println("Quadratic displacement-change approximation:");
        runTest(numberOfSteps, duration, freeFallDisplacementQuadTest);
        System.out.println("\n");

        System.out.println("Linear velocity-change approximation:");
        runTest(numberOfSteps, duration, freeFallVelocityLinTest);
        System.out.println("");

        System.out.println("Quadratic velocity-change approximation:");
        runTest(numberOfSteps, duration, freeFallVelocityQuadTest);
    }

    private void runOscillatorTests() {
        final StateComparisonTest<Double> oscillatorDisplacementLinTest = buildOscillatorDisplacementTest(false);
        final StateComparisonTest<Double> oscillatorVelocityLinTest = buildOscillatorVelocityTest(false);

        final StateComparisonTest<Double> oscillatorDisplacementQuadTest = buildOscillatorDisplacementTest(true);
        final StateComparisonTest<Double> oscillatorVelocityQuadTest = buildOscillatorVelocityTest(true);

        final double duration = 10;
        final int[] numberOfSteps = {10, 100, 1000, 10000, 100000, 1000000};

        System.out.println("Oscillator comparison tests");
        System.out.println("---------------------------");
        System.out.println("\n");

        System.out.println("Linear displacement-change approximation:");
        runTest(numberOfSteps, duration, oscillatorDisplacementLinTest);
        System.out.println("");

        System.out.println("Quadratic displacement-change approximation:");
        runTest(numberOfSteps, duration, oscillatorDisplacementQuadTest);
        System.out.println("\n");

        System.out.println("Linear velocity-change approximation:");
        runTest(numberOfSteps, duration, oscillatorVelocityLinTest);
        System.out.println("");

        System.out.println("Quadratic velocity-change approximation:");
        runTest(numberOfSteps, duration, oscillatorVelocityQuadTest);
    }

    private StateComparisonTest<Double> buildFreeFallDisplacementTest(boolean quadSpace) {
        final StateSingleValueSelector ySelector = (State state) -> state.y();
        return new FreeFallComparisonTest(1000, quadSpace, "Free falling particle displacement", "Average y-coordinate difference", ySelector);
    }

    private StateComparisonTest<Double> buildFreeFallVelocityTest(boolean quadSpace) {
        final StateSingleValueSelector vySelector = (State state) -> state.vy();
        return new FreeFallComparisonTest(1000, quadSpace, "Free falling particle velocity", "Average y-velocity difference", vySelector);
    }

    private StateComparisonTest<Double> buildOscillatorDisplacementTest(boolean quadSpace) {
        final double k = 4*Math.PI*Math.PI; // With unit mass this gives a period of 1 sec.
        final StateSingleValueSelector xSelector = (State state) -> state.x();
        return new OscillatorComparisonTest(k, 1, quadSpace, "Oscillator displacement", "Average x-coordinate difference", xSelector);
    }

    private StateComparisonTest<Double> buildOscillatorVelocityTest(boolean quadSpace) {
        final double k = 4*Math.PI*Math.PI; // With unit mass this gives a period of 1 sec.
        final StateSingleValueSelector vxSelector = (State state) -> state.vx();
        return new OscillatorComparisonTest(k, 1, quadSpace, "Oscillator velocity", "Average x-velocity difference", vxSelector);
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
