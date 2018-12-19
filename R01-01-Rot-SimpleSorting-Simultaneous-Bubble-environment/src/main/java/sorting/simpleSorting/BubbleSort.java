package sorting.simpleSorting;

import sorting.AbstractSorting;
import util.Util;

/**
 * 
 * @author Matheus Silva Araujo - Universidade Federal de Campina Grande
 *
 */

/**
 * The bubble sort algorithm iterates over the array multiple times, pushing big
 * elements to the right by swapping adjacent elements, until the array is
 * sorted.
 */
public class BubbleSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		//Verifica se os indices fornecidos sao validos
		if(leftIndex >= 0 && rightIndex < array.length) {
			//Primeira passada no array a fim percorrer em primeira dimensao os elementos
			for(int i = leftIndex; i < rightIndex; i++) {
				//Segunda passada no array a fim de comparar os elementos com o seu sucessor
				for(int j = leftIndex; j < rightIndex; j++) {
					//Comparacao dos elementos com o seu sucessor
					if(array[j].compareTo(array[j+1]) > 0) {
						//Se for maior, ha a troca
						Util.swap(array, j, j+1);
					}
				}
			}
		}
	}
}