package edu.spbu.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by artemaliev on 07/09/15.
 */
public class IntSort {
  public static int[] merge(int array1[], int array2[]) {
    int len1 = array1.length;
    int len2 = array2.length;
    int len = len1 + len2;
    int k = 0;
    int l = 0;
    int result[] = new int[len];
    for (int i = 0; i < len; i++) {
      if (l < len2 && k < len1) {
        if (array1[k] > array2[l]) result[i] = array2[l++];
        else result[i] = array1[k++];
      } else if (l < len2) {
        result[i] = array2[l++];
      } else {
        result[i] = array1[k++];
      }
    }
    return result;
  }

  public static void sort (int array[]) {
    int len = array.length;
    int n = 1;
    int jump;
    int am;
    int array2[];
    while (n < len) {
      jump = 0;
      while (jump < len) {
        if (jump + n >= len) break;
        am = (jump + n * 2 > len) ? (len - (jump + n)) : n;
        array2 = merge(Arrays.copyOfRange(array, jump, jump + n),
                Arrays.copyOfRange(array, jump + n, jump + n + am));
        for (int i = 0; i < n + am; ++i)
          array[jump + i] = array2[i];
        jump += n * 2;
      }
      n *= 2;
    }
  }

  public static void sort (List<Integer> list) {
    Collections.sort(list);
  }
}




