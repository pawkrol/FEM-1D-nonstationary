package org.pawkrol.academic.mes.project2;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SymmLQ;
import org.apache.commons.math3.util.DoubleArray;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pawkrol on 12/30/16.
 */
public class FileWriter {

    private HashMap<Double, RealVector> timeVector;
    private Path file;

    public FileWriter(){
        timeVector = new HashMap<>();
        file = Paths.get("output.txt");
    }

    public void addTimeVector(double time, RealVector vector){
        timeVector.put(time, vector);
    }

    public void writePretty(){
        List<String> lines = new ArrayList<>();
        timeVector.forEach((t, v) -> {
            lines.add("T = " + t);

            String csvLine = "H = { ";
            for (int i = 0; i < v.getDimension(); i++){
                csvLine += String.format("%.2f ", v.getEntry(i));
            }
            csvLine += "}\n";

            lines.add(csvLine);
        });

        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCSV(char separator){
        List<String> lines = new ArrayList<>();
        timeVector.forEach((t, v) -> {
            String csvLine = "" + t + separator;
            for (int i = 0; i < v.getDimension(); i++){
                csvLine += String.format("%.2f" + separator, v.getEntry(i));
            }

            lines.add(csvLine);
        });

        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
