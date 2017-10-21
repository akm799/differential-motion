package uk.co.akm.test.motion;

import uk.co.akm.test.motion.format.FixedWidthTextTable;
import uk.co.akm.test.motion.format.TextTable;
import uk.co.akm.test.motion.test.TestRun;
import uk.co.akm.test.motion.test.TestRunner;
import uk.co.akm.test.motion.test.examples.*;

/**
 * Created by Thanos Mavroidis on 02/09/2017.
 */
public class App {
    private static final String COLUMN_SPACING = "  ";

    private final TestRunner testRunner = new TestRunner();

    public static void main(String[] args) {
        final App app = new App();
        app.runAllTests();
    }

    private void runAllTests() {
        runTest("Firecracker (on the floor)", new FirecrackerTestRun());
        System.out.println();

        runTest("Projectile", new ProjectileTestRun());
        System.out.println();

        runTest("Free fall in air", new FreeFallInAirTestRun());
        System.out.println();

        runTest("Projectile in air", new ProjectileInAirTestRun());
        System.out.println();

        runTest("Projectile in wind", new ProjectileInWindTestRun());
        System.out.println();

        runTest("Satellite", new SatelliteTestRun());
        System.out.println();

        runTest("Charged particle in magnetic field", new ChargeNearMagnetTestRun());
        System.out.println();

        runTest("Particle race", new ParticleRaceTestRun());
    }

    private void runTest(String title, TestRun testRun) {
        System.out.println(title + " test:");
        final TextTable table = new FixedWidthTextTable(COLUMN_SPACING);
        testRunner.runTests(testRun, table);
        table.print(System.out);
    }
}
