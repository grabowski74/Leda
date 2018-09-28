package adt.stack;

public class StackImpl<T> implements Stack<T> {
	
	private static final int NOT = -1;
	private static final int ZERO = 0;
	private static final int ONE = 1;
	private int aux;
	private T[] array;
	private int top;

	@SuppressWarnings("unchecked")
	public StackImpl(int size) {
		array = (T[]) new Object[size];
		top = -1;
		aux = size;
	}

	@Override
	public T top() {
		T element = null;
		if (isEmpty())
			element = array[top];
		return element;
	}

	@Override
	public boolean isEmpty() {
		return top == NOT;
	}

	@Override
	public boolean isFull() {
		return top + ONE == aux;
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (isFull())
			throw new StackOverflowException();
		if (element != null) {
			top++;
			array[top] = element;
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (isEmpty())
			throw new StackUnderflowException();
		T element = array[top];
		top--;
		return element;
	}
}
