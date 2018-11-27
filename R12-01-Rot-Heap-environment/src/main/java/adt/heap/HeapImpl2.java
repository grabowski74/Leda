package adt.heap;

import java.util.Arrays;
import java.util.Comparator;
import util.Util;

public class HeapImpl2<T extends Comparable<T>> implements Heap<T>{
	
	protected T[] heap;
	protected int index =-1;
	
	protected Comparator<T> comparator;
	private int k;
	private int k2;
	
	@SuppressWarnings("unchecked")
	public HeapImpl2(Comparator<T> comparator) {
		this.heap = (T[]) (new Comparable[20]);
		this.comparator = comparator;
	}

	@Override
	public boolean isEmpty() {
		return (index == -1);
	}

	@Override
	public void insert(T element) {
		if(index == heap.length - 1) {
			heap = Arrays.copyOf(heap, heap.length + 10);
		}
		
		if(element != null) {
			this.heap[++this.index] = element;
			int position = this.index;
			while (getBiggestElementIndex(this.heap, position, this.parent(position)) == position
                    && this.parent(position) != position) {
				Util.swap(this.heap, position, this.parent(position));
				position = this.parent(position);
			}
		}
	}

	@Override
	public T extractRootElement() {
		T element = this.rootElement();
		this.remove(0);
		return element;
	}

	@Override
	public T rootElement() {
		return this.heap[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] heapsort(T[] array) {
		Comparator<T> comparator = this.comparator;
		
		this.heap = (T[]) (new Comparable[20]);
		this.comparator = (a, b) -> b.compareTo(a);
		this.index = -1;
		
		for(T element : array) this.insert(element);
		
		T[] anyArray = (T[]) (new Comparable[this.size()]);
		for(int index = 0; index < anyArray.length; index++) {
			anyArray[index] = this.extractRootElement();
		}
		
		this.comparator = comparator;
		
		return anyArray;
	}

	@Override
	public void buildHeap(T[] array) {
		this.heap = array;
		this.index = array.length - 1;
		for (int position = this.parent(array.length - 1); position >= 0; position--) {
			this.heapify(position);
		}
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] resp = (T[]) new Comparable[index + 1];
		for (int i = 0; i <= index; i++) {
			resp[i] = this.heap[i];
		}
		return resp;
	}

	@Override
	public int size() {
		return this.index + 1;
	}
	
	private int getBiggestElementIndex(T[] heap2, int first, int second) {
		return (this.comparator.compare(heap2[first], heap2[second]) > 0) ? first : second;
	}
	
	private void remove(int elementIndex) {
		if(this.index >= 0) {
			Util.swap(this.heap, elementIndex, this.index--);
			this.heapify(elementIndex);
		}
		
	}

	private void heapify(int elementIndex) {
		int left = this.left(elementIndex);
		int right = this.right(elementIndex);
		int biggest = elementIndex;
		
		if(left <= this.index) {
			biggest = left;
			if(right <= this.index) {
				biggest = getBiggestElementIndex(this.heap, left, right);
			}
		}
		
		biggest = getBiggestElementIndex(this.heap, elementIndex, biggest);
		
		if(elementIndex != biggest) {
			Util.swap(this.heap, elementIndex, biggest);
			this.heapify(biggest);
		}
	}

	private int right(int elementIndex) {
		return (elementIndex * 2 + 1) + 1;
	}

	private int left(int elementIndex) {
		return (elementIndex * 2 + 1);
	}
	
	private int parent(int position) {
		return (position - 1)/2;
	}

	@Override
	public int kth(int k) {
		heapsort(this.heap);
		return (int) this.heap[k];
	}
	
	
}