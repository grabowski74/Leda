package sorting.variationsOfBubblesort;

import sorting.AbstractSorting;
import util.Util;

/**
 * The combsort algoritm.
 */
public class CombSort<T extends Comparable<T>> extends AbstractSorting<T> {
	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		
		int gap = rightIndex - leftIndex + 1;
		boolean swap = true;

		while (gap > 1 || swap) {
			if (gap > 1)
				gap = (int) (gap / 1.25);
			int aux = leftIndex;
			swap = false;
			while (aux + gap <= rightIndex) {
				if (array[aux].compareTo(array[aux + gap]) > 0) {
					Util.swap(array, aux, aux + gap);
					swap = true;
				}
				aux++;
			}
		}
	}
}
