package sorting.simpleSorting;

import sorting.AbstractSorting;
import util.Util;

/**
 * 
 * @author Matheus Silva Araujo - Universidade Federal de Campina Grande
 *
 */

/**
 * As the insertion sort algorithm iterates over the array, it makes the
 * assumption that the visited positions are already sorted in ascending order,
 * which means it only needs to find the right position for the current element
 * and insert it there.
 */
public class InsertionSort<T extends Comparable<T>> extends AbstractSorting<T> {

	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		//Verifica se os indices fornecidos sao validos
		if(leftIndex >= 0 && rightIndex < array.length) {
			//Primeira passagem pelo array
			for(int i = leftIndex + 1; i <= rightIndex; i++) {
				//Atribui um key generico como auxiliar
				T key = array[i];
				int j = i - 1;
				//Move os elementos do array, que sao maiores que o auxiliar, para 
				//uma posicao acima da posicao corrente
				while(j >= leftIndex && array[j].compareTo(key) > 0) {
					//Troca
					Util.swap(array, j, j+1);
					j--;
				}
			}
		}
	}
}