package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * 
 * @author Matheus Silva Araujo - Universidade Federal de Campina Grande
 *
 */

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure
 * evitar desperdicio de memoria alocando o array de contadores com o tamanho
 * sendo o máximo inteiro presente no array a ser ordenado.
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		
		if (array != null && leftIndex < rightIndex && rightIndex >= 0 && leftIndex < array.length
				&& array.length != 1) {
			int maior = 0;
			int menor = array[leftIndex];
			int[] b = new int[array.length];
			
			for (int i = leftIndex; i <= rightIndex; i++) {
				if (array[i] < menor) {
					menor = array[i];
				}
				if (array[i] > maior) {
					maior = array[i];
				}
			}
			
			int[] c = new int[maior - menor + 1];
			
			for (int i = leftIndex; i <= rightIndex; i++) {
				c[array[i] - menor]++;
			}
			
			for (int i = 1; i < c.length; i++) {
				c[i] = c[i] + c[i - 1];
			}
			
			for (int i = rightIndex; i >= leftIndex; i--) {
				b[--c[array[i] - menor] + leftIndex] = array[i];
			}

			for (int i = leftIndex; i <= rightIndex; i++) {
				array[i] = b[i];
			}
		}
	}
}
