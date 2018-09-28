package adt.queue;

public class QueueImpl<T> implements Queue<T> {
	
	private static final int NOT = -1;
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private T[] array;
	private int tail;
	private int aux;

	@SuppressWarnings("unchecked")
	public QueueImpl(int size) {
		array = (T[]) new Object[size];
		tail = -1;
		aux = size;
	}

	@Override
	public T head() {
		T elem = null;
		if (tail != NOT)
			elem = array[ZERO];
		return elem;
	}

	@Override
	public boolean isEmpty() {
		return tail == NOT;
	}

	@Override
	public boolean isFull() {
		return tail + ONE == aux;
	}

	private void shiftLeft() {
		int i = ONE;
		while (i < aux && array[i] != null) {
			array[i - ONE] = array[i];
			i++;
		}
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (isFull())
			throw new QueueOverflowException();
		if (element != null) {
			tail++;
			array[tail] = element;
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty())
			throw new QueueUnderflowException();
		T elem = array[ZERO];
		this.shiftLeft();
		tail--;
		return elem;
	}

}
