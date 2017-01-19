package org.pawkrol.academic.mes.project2.writer;

import org.apache.commons.math3.linear.RealVector;

/**
 * Created by pawkrol on 2017-01-18.
 */
public class TimeVectorPair {

    private double t;
    private RealVector vector;

    public TimeVectorPair(double t, RealVector vector) {
        this.t = t;
        this.vector = vector;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    public RealVector getVector() {
        return vector;
    }

    public void setVector(RealVector vector) {
        this.vector = vector;
    }
}
