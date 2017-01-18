package org.pawkrol.academic.mes.project2.entities;

import org.apache.commons.math3.linear.RealVector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawel on 2016-12-15.
 */
public class Mesh {

    private List<Element> elements;
    private List<Node> nodes;

    private int n;
    private double rMin;
    private double rMax;
    private double tauMax;
    private float alpha;
    private float tempBegin;
    private float tempAir;
    private float c;
    private float ro;
    private float k;

    public void build(){
        buildNodes();
        buildElements();
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getrMin() {
        return rMin;
    }

    public void setrMin(double rMin) {
        this.rMin = rMin;
    }

    public double getrMax() {
        return rMax;
    }

    public void setrMax(double rMax) {
        this.rMax = rMax;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getTempBegin() {
        return tempBegin;
    }

    public void setTempBegin(float tempBegin) {
        this.tempBegin = tempBegin;
    }

    public float getTempAir() {
        return tempAir;
    }

    public void setTempAir(float tempAir) {
        this.tempAir = tempAir;
    }

    public float getC() {
        return c;
    }

    public void setC(float c) {
        this.c = c;
    }

    public float getRo() {
        return ro;
    }

    public void setRo(float ro) {
        this.ro = ro;
    }

    public float getK() {
        return k;
    }

    public void setK(float k) {
        this.k = k;
    }

    public double getTauMax() {
        return tauMax;
    }

    public void setTauMax(double tauMax) {
        this.tauMax = tauMax;
    }

    public double getDr() {
        int ne = n - 1;
        return (rMax - rMin) / ne;
    }

    public List<Element> getElements() {
        return elements;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void updateTemps(RealVector temps){
        int i = 0;
        for (Node n : nodes){
            n.setTemp(temps.getEntry(i++));
        }
    }

    private void buildElements(){
        elements = new ArrayList<>();

        for (int i = 0; i < nodes.size() - 1; i++){
            elements.add(new Element(nodes.get(i), nodes.get(i + 1)));
        }
    }

    private List<Node> buildNodes(){
        nodes = new ArrayList<>();
        double r = rMin;
        double dr = getDr();

        for (int i = 0; i < n; i++){
            Node n = new Node();
            n.setR(r);
            n.setIndex(i);
            n.setType(Node.Type.NORMAL);
            n.setTemp(tempBegin);

            nodes.add(n);

            r += dr;
        }

        nodes.get(nodes.size() - 1).setType(Node.Type.CONVECTION);

        return nodes;
    }
}
