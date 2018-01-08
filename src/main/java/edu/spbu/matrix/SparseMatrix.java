package edu.spbu.matrix;

import java.util.*;
import java.io.*;


public class SparseMatrix  implements Matrix {

  public int size;
  public Map<Integer, row> map;



  public SparseMatrix(Map<Integer, row> m, int size) {  // Defining a matrix by input map and size
    this.size = size;
    this.map = m;
  }

  public SparseMatrix(int size) {                       // Defining a matrix by size only
    this.size = size;
    this.map = new HashMap<>();
  }

  public SparseMatrix(BufferedReader s) {               //Reading a matrix into a hash map
    try {

      String temp = s.readLine();
      String[] arr = temp.split(" ");
      int k = arr.length;
      double number;
      size = k;
      map = new HashMap<Integer, row>();
      row tmap = new row();

      for (int j = 0; j < size; j++) {
        number = Double.parseDouble(arr[j]);
        if (number != 0.0) {
          tmap.put(j, number); //registering
        }
      }
      if (!tmap.isEmpty()) {
        map.put(0, tmap);
      }


      for (int i = 1; i < size; i++) {

        temp = s.readLine();
        arr = temp.split(" ");
        tmap = new row();
        for (int j = 0; j < size; j++) {
          number = Double.parseDouble(arr[j]);
          if (number != 0.0) {
            tmap.put(j, number);
          }
        }
        if (!tmap.isEmpty()) {
          map.put(i, tmap);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Matrix multiplication(Matrix other) {
    if (other instanceof SparseMatrix) return this.multiplicationSS((SparseMatrix) other);
    else return this.multiplicationSD((DenseMatrix) other);
  }

  public SparseMatrix SparseTrans() { //Transpose of a matrix
    Iterator<Map.Entry<Integer, row>> iter = map.entrySet().iterator(); // getting the rows
    HashMap<Integer, row> matrixTr = new HashMap<Integer, row>();
    while (iter.hasNext()) {
      Map.Entry entry = iter.next();
      Integer keyRow = (Integer) entry.getKey();// row number
      HashMap<Integer, row> value = (HashMap<Integer, row>) entry.getValue(); // elements of the row
      Iterator iterRow = value.entrySet().iterator(); // each element
      while (iterRow.hasNext()) {
        row RowTr = new row();
        Map.Entry entryRow = (Map.Entry) iterRow.next();
        Integer keyElements = (Integer) entryRow.getKey();
        Double valueElements = (Double) entryRow.getValue();
        RowTr = matrixTr.get(keyElements);
        if (RowTr == null) {
          RowTr = new row();
        }
        RowTr.put(keyRow, valueElements);
        matrixTr.put(keyElements, RowTr);
      }

    }
    return new SparseMatrix(matrixTr, size);
  }

  public SparseMatrix multiplicationSS(SparseMatrix other) {

    SparseMatrix resS = new SparseMatrix(size);
    Iterator<Map.Entry<Integer, row>> iter1 = this.map.entrySet().iterator();
    other = other.SparseTrans();
    while (iter1.hasNext()) {
      Map.Entry entry1 = iter1.next();
      Integer key1 = (Integer) entry1.getKey();
      HashMap<Integer, Double> value1 = (HashMap<Integer, Double>) entry1.getValue();// rows of the first matrix
      row resRow = new row();
      Iterator<Map.Entry<Integer, row>> iter2 = other.map.entrySet().iterator();
      while (iter2.hasNext()) {
        Map.Entry entry2 = iter2.next();
        Integer key2 = (Integer) entry2.getKey();
        HashMap<Integer, Double> value2 = (HashMap<Integer, Double>) entry2.getValue();// rows of the second matrix
        Iterator iterElement = value1.entrySet().iterator();
        double resValue = 0;
        while (iterElement.hasNext()) {
          Map.Entry entryElement = (Map.Entry) iterElement.next();
          Integer keyElement1 = (Integer) entryElement.getKey();
          Double valueElement1 = (Double) entryElement.getValue();
          if (value2.get(keyElement1) != null) {
            double a = value2.get(keyElement1);
            resValue = resValue + valueElement1 * a;
          }
        }
        if (resValue != 0) {
          resRow.put(key2, resValue);
        }
      }
      if (!resRow.isEmpty()) {
        resS.map.put(key1, resRow);
      }
    }
    return resS;
  }

  public SparseMatrix multiplicationSD(DenseMatrix other) {
    SparseMatrix res = new SparseMatrix(size);
    other = other.DenseTrans();
    double[][] a = other.matrix;
    Iterator<Map.Entry<Integer, row>> iter1 = this.map.entrySet().iterator();// Iterator of a sparse matrix
    while (iter1.hasNext()) {
      Map.Entry entry1 = iter1.next();
      Integer key1 = (Integer) entry1.getKey();
      HashMap<Integer, Double> value1 = (HashMap<Integer, Double>) entry1.getValue();// Getting a specific row
      row resRow = new row();
      for (int i = 0; i < size; i++) {
        double resValue = 0.0;
        Iterator iterElement = value1.entrySet().iterator(); // Elements
        while (iterElement.hasNext()) {
          Map.Entry entryElement = (Map.Entry) iterElement.next();
          Integer keyElement = (Integer) entryElement.getKey();// Get the row pointer
          Double valueElement = (Double) entryElement.getValue();// value of the element
          if (other.matrix[i][keyElement] != 0.0) {
            resValue = resValue + valueElement * a[i][keyElement];
          }
        }
        if (resValue != 0.0) {
          resRow.put(i, resValue);
        }
      }
      if (!resRow.isEmpty()) {
        res.map.put(key1, resRow);
      }

    }

    return res;
  }


  public void printS(BufferedWriter matrix) {
    try {
      double e;
      for (int i = 0; i < size; i++) {
        row a = map.get(i);
        if (a != null) {
          for (int j = 0; j < size; j++) {
            if (a.get(j) != null) {
              e = a.get(j);
              matrix.write(e + " ");
            } else {
              matrix.write("0.0" + " ");
            }
          }
          matrix.write("\n");

        } else {
          for (int j = 0; j < size; j++) {
            matrix.write("0.0 ");
          }
          matrix.write("\n");
        }
      }
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }
  }



  public boolean equals(Object o) {
    boolean t = true;
    if(!(o instanceof SparseMatrix)){
      return false;
    }
    SparseMatrix other = (SparseMatrix) o;
    for (int i = 0; i < size; i++) {
      row a = this.map.get(i);
      row b = other.map.get(i);
      //System.out.print(a);
      // System.out.println(b);

      //for (int j = 0; j < size; j++) {
      //System.out.print("a ");
      // System.out.println(a.get(j));
      //  System.out.print("b ");
      // System.out.println(b.get(j));// }
      if (!(a == null) && !(b == null)) {
        if (!a.equals(b)) {
          t = false;
        }
      }
      else if ((a == null) ^ (b == null)) {
        t = false;
      }
    }
    return t;
  }
}
