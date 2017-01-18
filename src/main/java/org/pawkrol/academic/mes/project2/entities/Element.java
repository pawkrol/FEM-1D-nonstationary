package org.pawkrol.academic.mes.project2.entities;

import org.apache.commons.math3.linear.RealMatrix;

/**
 * Created by Pawel on 2016-12-15.
 */
public class Element {

    private Node n1;
    private Node n2;

    private RealMatrix lK;
    private RealMatrix lF;

    public Element(Node n1, Node n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public Node getN1() {
        return n1;
    }

    public void setN1(Node n1) {
        this.n1 = n1;
    }

    public Node getN2() {
        return n2;
    }

    public void setN2(Node n2) {
        this.n2 = n2;
    }

    public void setlK(RealMatrix lK) {
        this.lK = lK;
    }

    public RealMatrix getlK() {
        return lK;
    }

    public void setlF(RealMatrix lF) {
        this.lF = lF;
    }

    public RealMatrix getlF() {
        return lF;
    }
}
