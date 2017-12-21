package edu.spbu.matrix;

import java.io.*;

public class MatrixMain {
    public static void main(String[] args) {


        try {
            BufferedReader string = new BufferedReader(new FileReader("src/main/Sparse1"));
            SparseMatrix Sparse1 = new SparseMatrix(string);

            string = new BufferedReader(new FileReader("src/main/Sparse2"));
            SparseMatrix Sparse2 = new SparseMatrix(string);

            string = new BufferedReader(new FileReader("src/main/Dense1"));
            DenseMatrix Dense1 = new DenseMatrix(string);

            string = new BufferedReader(new FileReader("src/main/Dense2"));
            DenseMatrix Dense2 = new DenseMatrix(string);


            SparseMatrix s_s = (SparseMatrix) Sparse1.multiplication(Sparse2);
            BufferedWriter sp = new BufferedWriter(new FileWriter(("src/main/Sparse1Sparse2")));
            s_s.printS(sp);
            sp.close();

            DenseMatrix d_d = (DenseMatrix) Dense1.multiplication(Dense2);
            BufferedWriter dn = new BufferedWriter(new FileWriter(("src/main/Dense1Dense2")));
            d_d.printD(dn);
            dn.close();

            SparseMatrix d_s = (SparseMatrix) Dense1.multiplication(Sparse2);
            sp = new BufferedWriter(new FileWriter(("src/main/Dense1Sparse2")));
            d_s.printS(sp);
            sp.close();

            SparseMatrix s_d = (SparseMatrix) Sparse1.multiplication(Dense2);
            sp = new BufferedWriter(new FileWriter(("src/main/Sparse1Dense2")));
            s_d.printS(sp);
            sp.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

