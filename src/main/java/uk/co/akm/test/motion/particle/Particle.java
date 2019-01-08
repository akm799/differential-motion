package uk.co.akm.test.motion.particle;

/**
 * Abstraction of a particle state which includes positional, velocity and acceleration data in a 3D coordinate system.
 * The particle state is updated over small time increments using the most basic numerical integration.
 *
 * Created by Thanos Mavroidis on 02/09/2017.
 */
public abstract class Particle implements State {
    private static final boolean PARABOLIC_APPROXIMATION = false;

    protected double ax; // current x-axis acceleration component
    protected double ay; // current y-axis acceleration component
    protected double az; // current z-axis acceleration component

    private double vx; // current x-axis velocity
    private double vy; // current y-axis velocity
    private double vz; // current z-axis velocity

    private double x; // current x-coordinate
    private double y; // current y-coordinate
    private double z; // current z-coordinate

    private double l; // length traversed

    private double t; // current time

    private final boolean quadSpace;

    private StateMonitor stateMonitor;

    /**
     * Creates a particle in some initial state defined by the constructor arguments.
     *
     * @param vx0 the initial velocity along the x-axis
     * @param vy0 the initial velocity along the y-axis
     * @param vz0 the initial velocity along the z-axis
     * @param x0 the initial x-axis position
     * @param y0 the initial y-axis position
     * @param z0 the initial z-axis position
     */
    protected Particle(double vx0, double vy0, double vz0, double x0, double y0, double z0) {
        this(vx0, vy0, vz0, x0, y0, z0, false);
    }

    /**
     * Creates a particle in some initial state defined by the constructor arguments.
     *
     * @param vx0 the initial velocity along the x-axis
     * @param vy0 the initial velocity along the y-axis
     * @param vz0 the initial velocity along the z-axis
     * @param x0 the initial x-axis position
     * @param y0 the initial y-axis position
     * @param z0 the initial z-axis position
     */
    protected Particle(double vx0, double vy0, double vz0, double x0, double y0, double z0, boolean quadSpace) {
        this.ax = 0;
        this.ay = 0;
        this.az = 0;
        this.vx = vx0;
        this.vy = vy0;
        this.vz = vz0;
        this.x = x0;
        this.y = y0;
        this.z = z0;
        this.t = 0;

        this.quadSpace = quadSpace;
    }

    /**
     * Updates the particle state over a small time increment.
     *
     * @param dt the small time increment
     */
    public final void update(double dt) {
        updateState(dt);
        informListenerOfUpdate();
    }

    private void updateState(double dt) {
        updateAcceleration(dt);
        updateVelocityAndPosition(dt);
        t += dt;
    }

    /**
     * Implemented by the user to update acceleration data over a small time increment.
     * The velocity and position information will be updated subsequently with a very
     * basic numerical integration.
     *
     * @param dt the small time increment
     */
    protected abstract void updateAcceleration(double dt);

    private void updateVelocityAndPosition(double dt) {
        if (quadSpace) {
            updateVelocityAndPositionQuadSpace(dt);
        } else {
            updateVelocityAndPositionLinSpace(dt);
        }
    }

    private void updateVelocityAndPositionLinSpace(double dt) {
        vx += ax*dt;
        vy += ay*dt;
        vz += az*dt;

        final double dx = vx*dt;
        final double dy = vy*dt;
        final double dz = vz*dt;

        x += dx;
        y += dy;
        z += dz;

        final double dl = (PARABOLIC_APPROXIMATION ? parabolicLengthApproximation(ax, ay, az, dt) : linearLengthApproximation(dx, dy, dz));
        l += dl;
    }

    // This should, generally, yield better results (but take longer to calculate).
    private void updateVelocityAndPositionQuadSpace(double dt) {
        final double dvx = ax*dt;
        final double dvy = ay*dt;
        final double dvz = az*dt;

        final double vxm = vx + dvx/2;
        final double vym = vy + dvy/2;
        final double vzm = vz + dvz/2;

        vx += dvx;
        vy += dvy;
        vz += dvz;

        final double dx = vxm*dt;
        final double dy = vym*dt;
        final double dz = vzm*dt;

        x += dx;
        y += dy;
        z += dz;

        final double dl = (PARABOLIC_APPROXIMATION ? parabolicLengthApproximation(ax, ay, az, dt) : linearLengthApproximation(dx, dy, dz));
        l += dl;
    }

    // Simpler length estimation approximation which also is quicker to calculate.
    private double linearLengthApproximation(double dx, double dy, double dz) {
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    // Better length estimation approximation, which should, generally, give better
    // results, but which takes much longer to calculate.
    private double parabolicLengthApproximation(double ax, double ay, double az, double dt) {
        return LengthEstimator.parabolicArcLength(Math.sqrt(ax*ax + ay*ay + az*az), dt);
    }

    private void informListenerOfUpdate() {
        if (stateMonitor != null) {
            stateMonitor.stateUpdated(this);
        }
    }

    @Override
    public final double ax() {
        return ax;
    }

    @Override
    public final double ay() {
        return ay;
    }

    @Override
    public final double az() {
        return az;
    }

    @Override
    public final double vx() {
        return vx;
    }

    @Override
    public final double vy() {
        return vy;
    }

    @Override
    public final double vz() {
        return vz;
    }

    @Override
    public final double x() {
        return x;
    }

    @Override
    public final double y() {
        return y;
    }

    @Override
    public final double z() {
        return z;
    }

    @Override
    public final double length() {
        return l;
    }

    @Override
    public final double t() {
        return t;
    }

    public final void setStateMonitor(StateMonitor stateMonitor) {
        this.stateMonitor = stateMonitor;
    }

    public final void removeStateMonitor() {
        setStateMonitor(null);
    }
}
