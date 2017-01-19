package org.pawkrol.academic.mes.project2.writer;

import org.apache.commons.math3.linear.RealVector;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawkrol on 12/30/16.
 */
public class FileWriter {

    private List<TimeVectorPair> timeVector;
    private Path file;

    public FileWriter(){
        timeVector = new ArrayList<>();
        file = Paths.get("output.txt");
    }

    public void addTimeVector(double time, RealVector vector){
        timeVector.add(new TimeVectorPair(time, vector));
    }

    public void writePretty(){
        List<String> lines = new ArrayList<>();
        double t;
        RealVector v;

        for (TimeVectorPair tvp: timeVector){
            t = tvp.getT();
            v = tvp.getVector();

            lines.add("T = " + t);

            String csvLine = "H = { ";
            for (int i = 0; i < v.getDimension(); i++){
                csvLine += String.format("%.2f ", v.getEntry(i));
            }
            csvLine += "}\n";

            lines.add(csvLine);
        }

        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeCSV(char separator){
        List<String> lines = new ArrayList<>();
        double t;
        RealVector v;

        String titleLine = "time";
        for (int i = 0; i < timeVector.get(0).getVector().getDimension(); i++){
            titleLine += separator + "temp" + i;
        }
        lines.add(titleLine);

        for (TimeVectorPair tvp : timeVector){
            t = tvp.getT();
            v = tvp.getVector();

            String csvLine = String.format("%.1f", t);
            for (int i = 0; i < v.getDimension(); i++){
                csvLine += String.format("%c%.2f", separator, v.getEntry(i));
            }

            lines.add(csvLine);
        }

        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
