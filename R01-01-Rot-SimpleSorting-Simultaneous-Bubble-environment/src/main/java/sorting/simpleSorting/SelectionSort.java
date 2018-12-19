package sorting.simpleSorting;

import sorting.AbstractSorting;
import util.Util;

/**
 * 
 * @author Matheus Silva Araujo - Universidade Federal de Campina Grande
 *
 */

/**
 * The selection sort algorithm chooses the smallest element from the array and
 * puts it in the first position. Then chooses the second smallest element and
 * stores it in the second position, and so on until the array is sorted.
 */
public class SelectionSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		//Verifica se os indices fornecidos sao validos
		if(leftIndex >= 0 && rightIndex < array.length) {
			//Primeira passada no array
			for(int i = leftIndex; i <= rightIndex; i++) {
				//Variavel auxiliar para o indice q contenha o menor valor
				int minIndex = i;
				//Segunda passada no array a fim de achar o menor elemento
				for(int j = i; j <= rightIndex; j++) {
					//Comparação entre o elemento e o menor atual
					if(array[j].compareTo(array[minIndex]) > 0) {
						minIndex = j;
					}
				}
				//Troca
				Util.swap(array, minIndex, i);
			}
		}
	}
}