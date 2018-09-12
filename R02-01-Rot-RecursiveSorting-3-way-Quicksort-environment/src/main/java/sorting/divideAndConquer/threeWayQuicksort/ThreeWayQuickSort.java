package sorting.divideAndConquer.threeWayQuicksort;

import sorting.AbstractSorting;
import util.Util;

public class ThreeWayQuickSort<T extends Comparable<T>> extends
		AbstractSorting<T> {

	/**
	 * No algoritmo de quicksort, selecionamos um elemento como pivot,
	 * particionamos o array colocando os menores a esquerda do pivot 
	 * e os maiores a direita do pivot, e depois aplicamos a mesma estrategia 
	 * recursivamente na particao a esquerda do pivot e na particao a direita do pivot. 
	 * 
	 * Considerando um array com muitoe elementos repetidos, a estrategia do quicksort 
	 * pode ser otimizada para lidar de forma mais eficiente com isso. Essa melhoria 
	 * eh conhecida como quicksort tree way e consiste da seguinte ideia:
	 * - selecione o pivot e particione o array de forma que
	 *   * arr[l..i] contem elementos menores que o pivot
	 *   * arr[i+1..j-1] contem elementos iguais ao pivot.
	 *   * arr[j..r] contem elementos maiores do que o pivot. 
	 *   
	 * Obviamente, ao final do particionamento, existe necessidade apenas de ordenar
	 * as particoes contendo elementos menores e maiores do que o pivot. Isso eh feito
	 * recursivamente. 
	 **/
	@Override
	public void sort(T[] array, int leftIndex, int rightIndex) {
		
		if (array.length > 1 && rightIndex - leftIndex >= 1) {
			
			sortPivot(array, leftIndex, rightIndex);
			int posPivot = part(array, leftIndex + 1, rightIndex - 1);
			
			if (rightIndex - leftIndex > 2) {
				sort(array, leftIndex, posPivot - 1);
				sort(array, posPivot + 1, rightIndex);
			} else {
				sortThree(array, leftIndex, rightIndex);
			}
		}
	}
	
	private void sortPivot(T[] array, int inicio, int fim) {
		
		int mid = (inicio + fim) / 2;
		if (array[inicio].compareTo(array[mid]) > 0) {
			Util.swap(array, inicio, mid);
		}
		
		if (array[inicio].compareTo(array[fim]) > 0) {
			Util.swap(array, inicio, fim);
		}
		
		if (array[mid].compareTo(array[fim]) > 0) {
			Util.swap(array, mid, fim);
		}
		
		Util.swap(array, mid, fim - 1);
	}

	private void sortThree(T[] array, int leftIndex, int rightIndex) {
		
		int size = rightIndex - leftIndex;
		if (size > 0 && array[leftIndex].compareTo(array[rightIndex]) > 0) {
			Util.swap(array, leftIndex, rightIndex);
		}
		
		if (size > 1) {
			int mid = (leftIndex + rightIndex) / 2;
			if (array[leftIndex].compareTo(array[mid]) > 0) {
				Util.swap(array, leftIndex, mid);
			}
			
			if (array[mid].compareTo(array[rightIndex]) > 0) {
				Util.swap(array, mid, rightIndex);
			}
		}
		
	}

	private int part(T[] array, int inicio, int fim) {
		int j = inicio - 1;
		int i = inicio;
		T pivot = array[fim];

		while (i < fim && j < fim) {
			if (array[i].compareTo(pivot) < 0 && i > j) {
				j++;
				Util.swap(array, j, i);
			} else {
				i++;
			}
		}
		j++;
		while (fim > j) {
			Util.swap(array, fim, fim - 1);
			fim--;
		}
		return j;
	}
}
