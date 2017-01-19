package org.pawkrol.academic.mes.project2.entities;

/**
 * Created by pawkrol on 2017-01-18.
 */
public class ElementInfo{
    private int startNode;
    private int endNode;
    private int materialID;

    public int getStartNode() {
        return startNode;
    }

    public void setStartNode(int startNode) {
        this.startNode = startNode;
    }

    public int getEndNode() {
        return endNode;
    }

    public void setEndNode(int endNode) {
        this.endNode = endNode;
    }

    public int getMaterialID() {
        return materialID;
    }

    public void setMaterialID(int materialID) {
        this.materialID = materialID;
    }
}
