package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.RecursiveDoubleLinkedListImpl;

public class StackRecursiveDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> list;
	protected int size;

	public StackRecursiveDoubleLinkedListImpl(int size) {
		if (size < 0) {
			size = 0;
		}
		this.size = size;
		this.list = new RecursiveDoubleLinkedListImpl<>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (list.size() == this.size)
			throw new StackOverflowException();
		this.list.insertFirst(element);
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (list.isEmpty())
			throw new StackUnderflowException();
		T element = ((RecursiveDoubleLinkedListImpl<T>) list).getData();
		list.removeFirst();
		return element;
	}

	@Override
	public T top() {
		T element = null;
		if (!list.isEmpty())
			element = ((RecursiveDoubleLinkedListImpl<T>) list).getData();
		return element;
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean isFull() {
		return list.size() == this.size;
	}
}
