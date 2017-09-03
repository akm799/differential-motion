package uk.co.akm.test.motion.particle;

/**
 * Created by Thanos Mavroidis on 02/09/2017.
 */
public interface State {

    double ax();

    double ay();

    double az();

    double vx();

    double vy();

    double vz();

    double x();

    double y();

    double z();

    double length();

    double t();
}
