package sorting.simpleSorting;

import sorting.AbstractSorting;

/**
 * As the insertion sort algorithm iterates over the array, it makes the
 * assumption that the visited positions are already sorted in ascending order,
 * which means it only needs to find the right position for the current element
 * and insert it there.
 */
public class InsertionSort<T extends Comparable<T>> extends AbstractSorting<T> {


	public void sort(T[] array, int leftIndex, int rightIndex) {
		if(!(array == null || array.length == 0 || leftIndex > rightIndex || leftIndex < 0 || rightIndex > array.length -1)) {
			for(int i = leftIndex; i <= rightIndex; i++) {
				T aux = array[i];
				int j = i-1;
				
				while(j >=0  && array[j].compareTo(aux) > 0) {
					array[j+1] = array[j];
					j -= 1;
				}
				array[j+1] = aux;			
			}
		}
	}
}
