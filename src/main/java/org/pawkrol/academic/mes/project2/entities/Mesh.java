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
    private double tauMax;
    private double dtau;
    private float rMin;
    private float rMax;
    private float alpha;
    private float tempBegin;
    private float tempAir;

    public Mesh(){
        elements = new ArrayList<>();
        nodes = new ArrayList<>();
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public float getrMin() {
        return rMin;
    }

    public void setrMin(float rMin) {
        this.rMin = rMin;
    }

    public float getrMax() {
        return rMax;
    }

    public void setrMax(float rMax) {
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

    public double getTauMax() {
        return tauMax;
    }

    public void setTauMax(double tauMax) {
        this.tauMax = tauMax;
    }

    public double getDtau() {
        return dtau;
    }

    public void setDtau(double dtau) {
        this.dtau = dtau;
    }

    public float getDr() {
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

    public void buildElementsWithMaterial(int startNode, int endNode, Material material){
        for (int i = startNode; i < endNode; i++){
            elements.add(new Element(nodes.get(i), nodes.get(i + 1), material));
        }
    }

    public List<Node> buildNodes(){
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
