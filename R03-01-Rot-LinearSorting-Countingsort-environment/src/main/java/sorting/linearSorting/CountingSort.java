package sorting.linearSorting;

import sorting.AbstractSorting;

/**
 * Classe que implementa a estratégia de Counting Sort vista em sala. Procure
 * evitar desperdicio de memoria alocando o array de contadores com o tamanho
 * sendo o máximo inteiro presente no array a ser ordenado.
 * 
 */
public class CountingSort extends AbstractSorting<Integer> {

	@Override
	public void sort(Integer[] array, int leftIndex, int rightIndex) {
		if (array.length > 1 && rightIndex - leftIndex >= 1) {
			counting(array, leftIndex, rightIndex);
		}
	}
	
	private void counting(Integer[] array, int leftIndex, int rightIndex) {
		
		//VERIFICANDO MAIOR E MENOR ELEMENTO DO ARRAY
		Integer maior = array[leftIndex];
		Integer	menor = array[leftIndex];
		
		for (Integer i : array) {
			if (i > maior) {      
				maior = i;
			}
			if (i < menor) {
				menor = i;
			}
		}
		
		//VARIÁVEIS AUXILIARES
		int aux = maior - menor + 1;
		int var = menor;
		
		//VETORES AUXILIARES
		int[] arrayAuxiliar = new int[aux];
		Integer[] outroArray = new Integer[rightIndex - leftIndex + 1];
		
		//CONSTRUCAO DO ARRAY DE INDICES
		for (int j = leftIndex; j <= rightIndex; j++) {
			arrayAuxiliar[array[j] - var]++;
		}
		
		//INCREMENTANDO OS INDICES
		for (int j = 1; j < aux; j++) {
			arrayAuxiliar[j] += arrayAuxiliar[j - 1];
		}
		
		//CONSTRUCAO DO ARRAY AUXILIAR DE RESPOSTA
		for (int j = rightIndex; j >= leftIndex; j--) {
			int cont = array[j] - var;
			outroArray[arrayAuxiliar[cont] - 1] = array[j];
			arrayAuxiliar[cont]--;
		}
		
		//TRANSFORMANDO O ARRAY INICIAL NO ARRAY ORDENADO
		for (int i = 0; i < array.length; i++)
			array[i + leftIndex] = outroArray[i];
		}	
		
}
	


		
