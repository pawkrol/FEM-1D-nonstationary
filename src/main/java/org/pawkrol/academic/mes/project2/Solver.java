package org.pawkrol.academic.mes.project2;

import org.apache.commons.math3.linear.*;
import org.pawkrol.academic.mes.project2.entities.Element;
import org.pawkrol.academic.mes.project2.entities.Mesh;
import org.pawkrol.academic.mes.project2.entities.Node;

import java.util.List;

/**
 * Created by Pawel on 2016-12-15.
 */
public class Solver {

    private final int[] W = {1, 1};

    private final double[] E = {-0.5773502692, 0.5773502692};

    private final double N[][] = {
            {0.5 * (1 - E[0]), 0.5 * (1 - E[1])},
            {0.5 * (1 + E[0]), 0.5 * (1 + E[1])}
    };

    private int np = W.length;

    private double timeStep;
    private double dr;
    private double rMax;
    private double factor1;
    private double factor2;
    private float alphaAir;
    private float tempAir;

    private FileWriter writer;

    public void solve(Mesh mesh){
        initialize(mesh);
        double endTime = mesh.getTauMax();
        List<Element> elements = mesh.getElements();
        writer = new FileWriter();

        RealMatrix K = null;
        RealMatrix F = null;
        RealVector solution = null;

        for (double t = 0; t <= endTime; t += timeStep){
            elements.forEach(this::calculateLocalKandF);
            K = createGKMatrix(mesh);
            F = createGFMatrix(mesh);

            DecompositionSolver solver = new LUDecomposition(K).getSolver();
            RealVector vector = F.scalarMultiply(-1).getColumnVector(0);
            solution = solver.solve(vector);

            mesh.updateTemps(solution);
            writer.addTimeVector(t, solution);
        }

        writer.writeCSV(',');
        displayMatrixForm(K, F);

        System.out.println("\nSolution:");
        displayVectorHorizontal("t", solution);
    }

    private void initialize(Mesh mesh) {
//        float a = mesh.getK() / (mesh.getC() * mesh.getRo());
        dr = mesh.getDr();
        rMax = mesh.getrMax();
//        timeStep = (dr * dr) / (0.5 * a);
        timeStep = 50;
        factor1 = mesh.getK() / dr;
        factor2 = (mesh.getC() * mesh.getRo() * dr) / timeStep;
        alphaAir = mesh.getAlpha();
        tempAir = mesh.getTempAir();
    }

    private void calculateLocalKandF(Element e){
        RealMatrix K = MatrixUtils.createRealMatrix(2, 2);
        RealMatrix F = MatrixUtils.createRealMatrix(2, 1);

        double rp;
        double K00 = 0, K01 = 0, K10, K11 = 0;
        double F1 = 0, F2 = 0;
        double rpwp = 0;

        for (int i = 0; i < np; i++){
            rp = N[0][i] * e.getN1().getR() + N[1][i] * e.getN2().getR();

            rpwp += rp * W[i];

            K00 += N[0][i] * N[0][i] * rp * W[i];
            K01 += N[0][i] * N[1][i] * rp * W[i];
            K11 += N[1][i] * N[1][i] * rp * W[i];

            F1 += (N[0][i] * e.getN1().getTemp() + N[1][i] * e.getN2().getTemp()) * N[0][i] * rp * W[i];
            F2 += (N[0][i] * e.getN1().getTemp() + N[1][i] * e.getN2().getTemp()) * N[1][i] * rp * W[i];
        }

        K10 = K01;

        K.setEntry(0, 0, factor1 * rpwp + factor2 * K00);
        K.setEntry(0, 1, -1. * factor1 * rpwp + factor2 * K01);
        K.setEntry(1, 0, -1. * factor1 * rpwp + factor2 * K10);
        K.setEntry(1, 1, factor1 * rpwp + factor2 * K11);

        F.setEntry(0, 0, -1. * factor2 * F1);
        F.setEntry(1, 0, -1. * factor2 * F2);

        if (e.getN2().getType() == Node.Type.CONVECTION){
            K.addToEntry(1, 1, 2 * alphaAir * rMax);
            F.addToEntry(1, 0, -2. * alphaAir * rMax * tempAir);
        }

        e.setlK(K);
        e.setlF(F);
    }

    private RealMatrix createGKMatrix(Mesh mesh){
        int nh = mesh.getN();
        RealMatrix gK = MatrixUtils.createRealMatrix(nh, nh);
        List<Element> elements = mesh.getElements();
        int idx1, idx2;

        for (Element e: elements){
            idx1 = e.getN1().getIndex();
            idx2 = e.getN2().getIndex();
            RealMatrix lK = e.getlK();

            gK.addToEntry(idx1, idx1, lK.getEntry(0, 0));
            gK.addToEntry(idx1, idx2, lK.getEntry(0, 1));
            gK.addToEntry(idx2, idx1, lK.getEntry(1, 0));
            gK.addToEntry(idx2, idx2, lK.getEntry(1, 1));
        }

        return gK;
    }

    private RealMatrix createGFMatrix(Mesh mesh){
        int nh = mesh.getN();
        RealMatrix gF = MatrixUtils.createRealMatrix(nh, 1);
        List<Element> elements = mesh.getElements();
        int idx1, idx2;

        for (Element e: elements) {
            idx1 = e.getN1().getIndex();
            idx2 = e.getN2().getIndex();
            RealMatrix lF = e.getlF();

            gF.addToEntry(idx1, 0, lF.getEntry(0, 0));
            gF.addToEntry(idx2, 0, lF.getEntry(1, 0));
        }

        return gF;
    }

    private void displayVectorHorizontal(String name, RealVector v){
        int length = v.getDimension();

        System.out.print("{" + name + "} = {");
        for (int i = 0; i < length; i++){
            System.out.printf("%10.3f", v.getEntry(i));
        }
        System.out.println("\t}");
    }

    private void displayMatrixForm(RealMatrix gK, RealMatrix gF){
        double[][] hData = gK.getData();
        double[][] pData = gF.getData();

        int hRows = gK.getRowDimension();
        int hCols = gK.getColumnDimension();
        int pRows = gF.getRowDimension();

        assert (hRows == pRows);

        for (int i = 0; i < hRows; i++){
            System.out.print("| ");

            for (int j = 0; j < hCols; j++){
                System.out.printf("%10.3f ", hData[i][j]);
            }

            System.out.print("\t|\t");

            if (i == (hRows/2)) System.out.print("*");

            System.out.printf("\t| t%d |\t", i + 1);

            if (i == (hRows/2)) System.out.print("+");

            System.out.printf("\t| %10.3f |", pData[i][0]);

            if (i == (hRows/2)) System.out.print("\t=\t0");

            System.out.println();
        }
    }
}
