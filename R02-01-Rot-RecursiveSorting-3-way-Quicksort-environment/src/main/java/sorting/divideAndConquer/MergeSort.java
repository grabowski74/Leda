package sorting.divideAndConquer;

import sorting.AbstractSorting;

/**
 * Merge sort is based on the divide-and-conquer paradigm. The algorithm
 * consists of recursively dividing the unsorted list in the middle, sorting
 * each sublist, and then merging them into one single sorted list. Notice that
 * if the list has length == 1, it is already sorted.
 */
public class MergeSort<T extends Comparable<T>> extends AbstractSorting<T> {

   @Override
   public void sort(T[] array, int leftIndex, int rightIndex) {
      if (array.length > 1 && rightIndex - leftIndex >= 1) {

         int mid = (leftIndex + rightIndex) / 2;

         sort(array, leftIndex, mid);
         sort(array, mid + 1, rightIndex);

         merge(array, leftIndex, mid, rightIndex);
      }
   }

   private void merge(T[] array, int inicio, int mid, int fim) {

      int parte1 = inicio;
      int parte2 = mid + 1;

      T[] aux = (T[]) new Comparable[fim - inicio + 1];

      int auxIndex = 0;
      while (parte1 <= mid && parte2 <= fim) {
         if (array[parte1].compareTo(array[parte2]) > 0) {
            aux[auxIndex] = array[parte2];
            parte2++;
         } else {
            aux[auxIndex] = array[parte1];
            parte1++;
         }
         auxIndex++;
      }

      if (parte1 <= mid) {
         copy(array, aux, parte1, mid, auxIndex);
      }
      if (parte2 <= fim) {
         copy(array, aux, parte2, fim, auxIndex);
      }
      copy(aux, array, 0, aux.length - 1, inicio);
   }

   private void copy(T[] aux, T[] array, int inicio, int fim, int comeco) {
      for (int p = inicio; p <= fim; p++, comeco++) {
         array[comeco] = aux[p];
      }
   }
}
