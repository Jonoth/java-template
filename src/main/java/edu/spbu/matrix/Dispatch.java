package edu.spbu.matrix;

public class Dispatch {
    int n=0;
    synchronized int next(){return n++;}
}

