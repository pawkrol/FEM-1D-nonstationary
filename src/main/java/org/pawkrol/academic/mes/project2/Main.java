package org.pawkrol.academic.mes.project2;

import org.pawkrol.academic.mes.project2.entities.MeshLoader;
import org.pawkrol.academic.mes.project2.entities.Mesh;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Pawel on 2016-12-15.
 */
public class Main {

    public static void main(String[] args){
        String file = readFile(Main.class.getResource("/data2.txt"));

        Mesh mesh = MeshLoader.loadMesh(file);
        mesh.build();

        Solver solver = new Solver();
        solver.solve(mesh);
    }

    public static String readFile(URL url){
        StringBuilder builder = new StringBuilder();

        try {
            Path path = Paths.get(url.toURI());
            Files.lines(path).forEachOrdered(l -> builder.append(l).append("\n"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

}
