package edu.spbu.matrix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DenseMatrix implements Matrix { //Creating a Dense Matrix class which implements Matrix interface
  public int size;                         //Declaring a size var
  public double matrix[][];                //Declaring an array, which represents the matrix


  public DenseMatrix(double[][] matrix, int size) {  //Defining a matrix by the matrix and its size
    this.size = size;
    this.matrix = matrix;
  }

  public DenseMatrix(int size) {                     //Defining a matrix by its size
    this.matrix = new double[size][size];
    this.size = size;
  }

  public DenseMatrix(BufferedReader string) {
    try {
      String temp = string.readLine();                       // Reading the first line of the input string
      String[] array = temp.split(" ");                // to determine the size of n x n matrix
      int k = array.length;
      this.size = k;                                         //  We know the size now
      this.matrix = new double[size][size];
      double number;

      for (int j = 0; j < k; j++) {                           // Recording the first row
        number = Double.parseDouble(array[j]);
        this.matrix[0][j] = number;
      }

      for (int i = 1; i < k; i++) {                           //Reading the other rows
        temp = string.readLine();
        array = temp.split(" ");

        for (int j = 0; j < k; j++) {
          number = Double.parseDouble(array[j]);
          this.matrix[i][j] = number;
        }
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();                           // If an exception is thrown, it gives info about the error
    }
  }


  public Matrix multiplication(Matrix other) {            // If the other matrix is Dense, then do the DD method, else SS
    if (other instanceof DenseMatrix) return this.multiplicationDD((DenseMatrix) other);
    else return this.multiplicationDS((SparseMatrix) other);
  }


 /*  public DenseMatrix multiplicationDD(DenseMatrix other) { //Multiplying Dense x Dense
        other = other.DenseTrans();
        DenseMatrix res = new DenseMatrix(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    res.matrix[i][j] += this.matrix[i][k] * other.matrix[j][k];
                }
            }
        }
        return res;
    }*/

  public DenseMatrix multiplicationDD(DenseMatrix other) {
    final DenseMatrix res = new DenseMatrix(size);
    final DenseMatrix first = this;
    other = other.DenseTrans();
    res.matrix = new double[first.matrix.length][first.matrix.length];
    Dispatch d = new Dispatch();
    final DenseMatrix final_m = other;

    class MyCode implements Runnable {
      public void run() {
        for (int i = d.next(); i < first.matrix.length; i = d.next()) {
          for (int j = 0; j < final_m.matrix.length; j++) {
            for (int k = 0; k < final_m.matrix.length; k++) {
              res.matrix[i][j] = res.matrix[i][j] + first.matrix[i][k] * final_m.matrix[j][k];
            }
          }
        }
      }

    }

    int core_count = Runtime.getRuntime().availableProcessors();
    Thread[] t = new Thread[core_count];
    for (int i = 0; i < core_count; i++) {  //Creating threads
      Thread t1 = new Thread(new MyCode());
      t[i] = t1;
    }
    for (int i = 0; i < core_count; i++) {  //Start all of them
      t[i].start();
    }
    for (int i = 0; i < core_count; i++) {
      try {
        t[i].join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    return res;

  }



  public SparseMatrix multiplicationDS(SparseMatrix other) {          //
    other = other.SparseTrans();
    SparseMatrix res = new SparseMatrix(size);
    for (int i = 0; i < size; i++) {
      row resRow = new row();
      Iterator<Map.Entry<Integer, row>> iter1 = other.map.entrySet().iterator();// Row iterator
      while (iter1.hasNext()) {
        Map.Entry entry1 = iter1.next();
        Integer key1 = (Integer) entry1.getKey();// key of the row
        HashMap<Integer, Double> value1 = (HashMap<Integer, Double>) entry1.getValue();// the row
        Iterator iterElement = value1.entrySet().iterator();// iterator of the elements
        double resValue = 0;
        while (iterElement.hasNext()) {
          Map.Entry entryElement = (Map.Entry) iterElement.next();
          Integer keyElement = (Integer) entryElement.getKey();// key
          Double valueElement = (Double) entryElement.getValue();//value
          resValue = resValue + this.matrix[i][keyElement] * valueElement;
        }
        if (resValue != 0) {
          resRow.put(key1, resValue);
        }
      }
      if (!resRow.isEmpty()) {
        res.map.put(i, resRow);
      }
    }
    return res;
  }


  public DenseMatrix DenseTrans() {              //Transpose of a Dense matrix
    double[][] mTr = new double[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = i; j < size; j++) {
        double aT = this.matrix[i][j];
        mTr[i][j] = this.matrix[j][i];
        mTr[j][i] = aT;
      }
    }
    return new DenseMatrix(mTr, size);
  }


  public void printD(BufferedWriter matrix) {  //Output of the matrix
    try {
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          matrix.write(this.matrix[i][j] + " ");

        }
        matrix.write("\n");
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }


  public boolean equals(Object o) {  //Checking for equality
    boolean t = true;
    if (!(o instanceof DenseMatrix)) {
      return false;
    }
    DenseMatrix other = (DenseMatrix) o;
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        //System.out.print(this.matrix[i][j]);
        //System.out.println(other.matrix[i][j]);
        if (this.matrix[i][j] != other.matrix[i][j]) {
          t = false;
          System.out.println(t);
        }
      }
    }
    System.out.println(t);
    return t;
  }
}


