package org.pawkrol.academic.mes.project2.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawkrol on 10/31/16.
 */
public class MeshLoader {

    public static Mesh loadMesh(String file){
        StringReader stringReader = new StringReader(file);
        BufferedReader reader = new BufferedReader(stringReader);

        Mesh mesh = new Mesh();

        mesh.setrMin(getFloatValue(reader));
        mesh.setrMax(getFloatValue(reader));
        mesh.setAlpha(getFloatValue(reader));
        mesh.setTempBegin(getFloatValue(reader));
        mesh.setTempAir(getFloatValue(reader));
        mesh.setTauMax(getDoubleValue(reader));
        mesh.setDtau(getDoubleValue(reader));

        int nodesN = getIntValue(reader);
        mesh.setN(nodesN);
        mesh.buildNodes();

        List<Material> materials = new ArrayList<>();
        int materialN = getIntValue(reader);
        for (int i = 0; i < materialN; i++){
            materials.add(getMaterial(reader));
        }

        int elementsGroups = getIntValue(reader);
        for (int i = 0; i < elementsGroups; i++){
            ElementInfo elementInfo = getElementInfo(reader);

            assert elementInfo != null;

            mesh.buildElementsWithMaterial(
                    elementInfo.getStartNode(),
                    elementInfo.getEndNode(),
                    materials.get(elementInfo.getMaterialID())
            );
        }

        return mesh;
    }

    private static double getDoubleValue(BufferedReader reader){
        String[] tokens;

        try {
            tokens = getTokens(reader);

            return Double.parseDouble(tokens[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static float getFloatValue(BufferedReader reader){
        String[] tokens;

        try {
            tokens = getTokens(reader);

            return Float.parseFloat(tokens[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static int getIntValue(BufferedReader reader){
        String[] tokens;

        try {
            tokens = getTokens(reader);

            return Integer.parseInt(tokens[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static Material getMaterial(BufferedReader reader){
        String[] tokens;
        Material material = new Material();

        try {
            tokens = getTokens(reader);

            material.setC(Float.parseFloat(tokens[0]));
            material.setRo(Float.parseFloat(tokens[1]));
            material.setK(Float.parseFloat(tokens[2]));

            return material;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static ElementInfo getElementInfo(BufferedReader reader){
        String[] tokens;
        ElementInfo elementInfo = new ElementInfo();

        try {
            tokens = getTokens(reader);

            elementInfo.setStartNode(Integer.parseInt(tokens[0]));
            elementInfo.setEndNode(Integer.parseInt(tokens[1]));
            elementInfo.setMaterialID(Integer.parseInt(tokens[2]));

            return elementInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String[] getTokens(BufferedReader reader) throws IOException{
        String line = reader.readLine();
        return line.split("\\s+");
    }
}