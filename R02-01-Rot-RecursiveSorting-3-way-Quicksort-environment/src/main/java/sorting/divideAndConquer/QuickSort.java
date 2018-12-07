package sorting.divideAndConquer;

import sorting.AbstractSorting;
import util.Util;

/**
 * 
 * @author Matheus Silva Araujo - Universidade Federal de Campina Grande
 *
 */

/**
 * Quicksort is based on the divide-and-conquer paradigm. The algorithm chooses
 * a pivot element and rearranges the elements of the interval in such a way
 * that all elements lesser than the pivot go to the left part of the array and
 * all elements greater than the pivot, go to the right part of the array. Then
 * it recursively sorts the left and the right parts. Notice that if the list
 * has length == 1, it is already sorted.
 */
public class QuickSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		if(leftIndex < rightIndex) {
			int pivoIndex = part(array, leftIndex, rightIndex);
			sort(array, leftIndex, pivoIndex - 1);
			sort(array, pivoIndex + 1, rightIndex);
		}
	}
	
	private int part(T[] array, int leftIndex, int rightIndex) {
		T pivo = array[leftIndex];
		int i, j;
		i = leftIndex + 1;
		j = rightIndex;
		
		while(i <= j) {
			if(array[i].compareTo(pivo) < 0) {
				i++;
			} else if(pivo.compareTo(array[j]) < 0) {
				j--;
			} else {
				Util.swap(array, i, j);
				i++;
				j--;
			}
		}
		array[leftIndex] = array[j];
		array[j] = pivo;
		return j;
	}
}
