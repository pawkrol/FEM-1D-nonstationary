package org.pawkrol.academic.mes.project2.entities;

/**
 * Created by pawkrol on 10/31/16.
 */
public class Node {

    public enum Type{
        NORMAL,
        STREAM,
        CONVECTION;

        public static Type fromInt(int id){
            switch (id){
                case 0:
                    return NORMAL;
                case 1:
                    return STREAM;
                case 2:
                    return CONVECTION;
            }
            return NORMAL;
        }
    }

    private int index;
    private double r;
    private double temp;
    private Type type;

    public Node() {}

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
