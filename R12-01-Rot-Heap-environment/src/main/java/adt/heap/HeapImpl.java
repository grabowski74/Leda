package adt.heap;

import java.util.Arrays;
import java.util.Comparator;

import util.Util;

/**
 * O comportamento de qualquer heap é definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Essa comparação não é feita diretamente com os elementos armazenados,
 * mas sim usando um comparator. Dessa forma, dependendo do comparator, a heap
 * pode funcionar como uma max-heap ou min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

    protected T[] heap;
    protected int index = -1;
    /**
     * O comparador é utilizado para fazer as comparações da heap. O ideal é
     * mudar apenas o comparator e mandar reordenar a heap usando esse
     * comparator. Assim os metodos da heap não precisam saber se vai funcionar
     * como max-heap ou min-heap.
     */
    protected Comparator<T> comparator;
    private static final int INITIAL_SIZE = 20;

    private static final int INCREASING_FACTOR = 10;
    private static final int EQUAL = 0;
    private static final int FIRST_INDEX = 0;

    @SuppressWarnings("unchecked")
    public HeapImpl(Comparator<T> comparator) {
        this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
        this.comparator = comparator;
    }


    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int left(int i) {
        return (i * 2 + 1);
    }


    private int right(int i) {
        return (i * 2 + 1) + 1;
    }

    @Override
    public boolean isEmpty() {
        return (index == -1);
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

    private void heapify(int position) {
        int left = this.left(position), right = this.right(position), biggest = position;
        if (left <= this.index) {
            biggest = left;
            if (right <= this.index) {
                biggest = getBiggestElementIndex(this.heap, left, right);
            }
        }
        biggest = getBiggestElementIndex(this.heap, position, biggest);

        if (position != biggest) {
            Util.swap(this.heap, position, biggest);
            this.heapify(biggest);
        }
    }

    private int getBiggestElementIndex(T[] array, int firstIndex, int secondIndex) {
        return (this.comparator.compare(array[firstIndex], array[secondIndex]) > EQUAL) ? firstIndex : secondIndex;
    }

    @Override
    public void insert(T element) {
        // ESSE CODIGO E PARA A HEAP CRESCER SE FOR PRECISO. NAO MODIFIQUE
        if (index == heap.length - 1) {
            heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
        }

        if (element != null) {
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
    public void buildHeap(T[] array) {
        this.heap = array;
        this.index = array.length - 1;
        for (int position = this.parent(array.length - 1); position >= FIRST_INDEX; position--) {
            this.heapify(position);
        }
    }

    @Override
    public T extractRootElement() {
        T element = this.rootElement();
        this.remove(FIRST_INDEX);
        return element;
    }

    private void remove(int elementIndex) {
        if (this.index >= FIRST_INDEX) {
            Util.swap(this.heap, elementIndex, this.index--);
            this.heapify(elementIndex);
        }
    }

    @Override
    public T rootElement() {
        return this.heap[FIRST_INDEX];
    }

    @Override
    public T[] heapsort(T[] array) {
        Comparator<T> comparator = this.comparator;

        this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
        this.comparator = (a, b) -> b.compareTo(a);
        this.index = -1;

        for (T element : array) this.insert(element);

        T[] newArray = (T[]) (new Comparable[this.size()]);
        for (int index = FIRST_INDEX; index < newArray.length; index++) {
            newArray[index] = this.extractRootElement();
        }

        this.comparator = comparator;
        return newArray;
    }

    @Override
    public int size() {
        return this.index + 1;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
        this.buildHeap(Arrays.copyOf(this.heap, this.size()));
    }

    public T[] getHeap() {
        return heap;
    }

}