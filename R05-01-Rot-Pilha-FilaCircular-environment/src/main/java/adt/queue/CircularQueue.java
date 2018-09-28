package adt.queue;

public class CircularQueue<T> implements Queue<T> {
	
	private static final int NOT = -1;
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private int aux;
	private T[] array;
	private int tail;
	private int head;
	private int elements;

	public CircularQueue(int size) {
		array = (T[]) new Object[size];
		head = -1;
		tail = -1;
		elements = 0;
		aux = size;
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (isFull())
			throw new QueueOverflowException();

		if (element != null) {
			if (tail == NOT)
				head = tail = ZERO;
			else
				tail = (tail + ONE) % aux;
			array[tail] = element;
			elements++;
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty())
			throw new QueueUnderflowException();
		T element = array[head];
		elements--;
		if (head == tail)
			head = tail = NOT;
		else
			head = (head + ONE) % aux;
		return element;
	}

	@Override
	public T head() {
		T elem = null;
		if (head != NOT)
			elem = array[head];
		return elem;
	}

	@Override
	public boolean isEmpty() {
		return elements == ZERO;
	}

	@Override
	public boolean isFull() {
		return elements == aux;
	}

}
