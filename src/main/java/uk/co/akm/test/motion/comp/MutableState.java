package uk.co.akm.test.motion.comp;

import uk.co.akm.test.motion.particle.State;

/**
 * Created by Thanos Mavroidis on 09/01/2019.
 */
public final class MutableState implements State {
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

    public void setAx(double ax) {
        this.ax = ax;
    }

    public void setAy(double ay) {
        this.ay = ay;
    }

    public void setAz(double az) {
        this.az = az;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public void setVz(double vz) {
        this.vz = vz;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setL(double l) {
        this.l = l;
    }

    public void setT(double t) {
        this.t = t;
    }

    @Override
    public double ax() {
        return ax;
    }

    @Override
    public double ay() {
        return ay;
    }

    @Override
    public double az() {
        return az;
    }

    @Override
    public double vx() {
        return vx;
    }

    @Override
    public double vy() {
        return vy;
    }

    @Override
    public double vz() {
        return vz;
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public double length() {
        return l;
    }

    @Override
    public double t() {
        return t;
    }
}
