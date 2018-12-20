package adt.queue;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;
import adt.linkedList.SingleLinkedListImpl;

public class QueueDoubleLinkedListImpl<T extends Comparable<T>> implements Queue<T> {

	protected DoubleLinkedList<T> list;
	protected int size;

	public QueueDoubleLinkedListImpl(int size) {
		this.size = size;
		this.list = new DoubleLinkedListImpl<T>();
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {

		if (isFull())
			throw new QueueOverflowException();

		this.list.insert(element);

	}

	@Override
	public T dequeue() throws QueueUnderflowException {

		if (isEmpty())
			throw new QueueUnderflowException();

		T result = ((SingleLinkedListImpl<T>) this.list).getHead().getData();

		this.list.removeFirst();

		return result;
	}

	@Override
	public T head() {

		T result = null;

		if (!isEmpty())
			result = ((SingleLinkedListImpl<T>) this.list).getHead().getData();

		return result;
	}

	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	@Override
	public boolean isFull() {
		return this.list.size() == this.size;
	}

}