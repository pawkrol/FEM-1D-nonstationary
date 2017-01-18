package org.pawkrol.academic.mes.project2.entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by pawkrol on 10/31/16.
 */
public class MeshLoader {

    public static Mesh loadMesh(String file){
        StringReader stringReader = new StringReader(file);
        BufferedReader reader = new BufferedReader(stringReader);

        Mesh mesh = new Mesh();

        mesh.setN(getIntValue(reader));
        mesh.setrMin(getDoubleValue(reader));
        mesh.setrMax(getDoubleValue(reader));
        mesh.setAlpha(getFloatValue(reader));
        mesh.setTempBegin(getFloatValue(reader));
        mesh.setTempAir(getFloatValue(reader));
        mesh.setC(getFloatValue(reader));
        mesh.setRo(getFloatValue(reader));
        mesh.setK(getFloatValue(reader));
        mesh.setTauMax(getDoubleValue(reader));

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

    private static String[] getTokens(BufferedReader reader) throws IOException{
        String line = reader.readLine();
        return line.split("\\s+");
    }
}