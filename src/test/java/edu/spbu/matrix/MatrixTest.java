package edu.spbu.matrix;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MatrixTest {
  BufferedReader s;
  SparseMatrix Sparse1;
  DenseMatrix Dense1;
  SparseMatrix Sparse2;
  DenseMatrix Dense2;
  SparseMatrix resultS1D2;
  SparseMatrix resultS1S2;
  SparseMatrix resultD1S2;
  DenseMatrix resultD1D2;



  public MatrixTest() {
    try {
      s = new BufferedReader(new FileReader("src/main/Sparse1"));
      Sparse1 = new SparseMatrix(s);
      s = new BufferedReader(new FileReader("src/main/Dense1"));
      Dense1 = new DenseMatrix(s);

      s = new BufferedReader(new FileReader("src/main/Sparse2"));
      Sparse2 = new SparseMatrix(s);
      s = new BufferedReader(new FileReader("src/main/Dense2"));
      Dense2 = new DenseMatrix(s);

      s = new BufferedReader(new FileReader("src/main/resultS1D2"));
      resultS1D2 = new SparseMatrix(s);
      s = new BufferedReader(new FileReader("src/main/resultS1S2"));
      resultS1S2 = new SparseMatrix(s);
      s = new BufferedReader(new FileReader("src/main/resultD1S2"));
      resultD1S2 = new SparseMatrix(s);
      s = new BufferedReader(new FileReader("src/main/resultD1D2"));
      resultD1D2 = new DenseMatrix(s);
    } catch (IOException e)

    {
      e.printStackTrace();
    }
  }


  @Test
  public void mulS1D2 () {
    SparseMatrix s1d2 = (SparseMatrix) Sparse1.multiplication(Dense2);
    Assert.assertTrue(resultS1D2.equals(s1d2));
  }

  @Test
  public void mulS1S2 () {
    SparseMatrix s1s2 = (SparseMatrix) Sparse1.multiplication(Sparse2);
    Assert.assertTrue(resultS1S2.equals(s1s2));

  }

  @Test
  public void mulD1S2 () {
    SparseMatrix d1s2 = (SparseMatrix) Dense1.multiplication(Sparse2);
    Assert.assertTrue(resultD1S2.equals(d1s2));

  }

  @Test
  public void mulD1D2 () {
    DenseMatrix d1d2 = (DenseMatrix) Dense1.multiplication(Dense2);
    Assert.assertTrue(resultD1D2.equals(d1d2));
  }


}

