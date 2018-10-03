package adt.queue;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class QueueDoubleLinkedListImpl<T> implements Queue<T> {

	private final int SIZE;

	protected DoubleLinkedList<T> list;

	public QueueDoubleLinkedListImpl(int size) {
		if (size < 0)
			size = 0;
		this.SIZE = size;
		this.list = new DoubleLinkedListImpl<>();
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (isFull())
			throw new QueueOverflowException();
		list.insert(element);
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty())
			throw new QueueUnderflowException();
		T element = head();
		list.removeFirst();
		return element;
	}

	@Override
	public T head() {
		T element = null;
		if (!isEmpty())
			element = ((DoubleLinkedListImpl<T>) list).getHead().getData();
		return element;
	}

	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	@Override
	public boolean isFull() {
		return this.SIZE == list.size();
	}
}