package edu.spbu.matrix;


import java.io.IOException;

public interface Matrix{
  Matrix multiplication(Matrix other) throws IOException, InterruptedException;
}
