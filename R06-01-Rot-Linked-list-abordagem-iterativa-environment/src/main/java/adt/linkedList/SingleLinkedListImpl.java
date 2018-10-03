package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return this.head.isNIL();
	}

	@Override
	public int size() {
		int size = 0;
		SingleLinkedListNode<T> aux = this.head;
		while (!aux.isNIL()) {
			size++;
			aux = aux.getNext();
		}
		return size;

	}

	@Override
	public T search(T element) {
		T result = null;
		if (!isEmpty() && element != null) {
			SingleLinkedListNode<T> aux = this.head;
			while (!aux.isNIL() && !aux.getData().equals(element)) {
				aux = aux.getNext();
			}
			if (!aux.isNIL() && aux.getData().equals(element)) {
				result = aux.getData();
			}
		}
		return result;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			SingleLinkedListNode<T> aux = this.head;
			while (!aux.isNIL())
				aux = aux.getNext();
			aux.setData(element);
			aux.setNext(new SingleLinkedListNode<T>());
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty() && element != null) {
			SingleLinkedListNode<T> pointer = this.head.getNext();
			while (!pointer.isNIL() && !pointer.getData().equals(element)) {
				pointer = pointer.getNext();
			}
			if (!pointer.isNIL()) {
				SingleLinkedListNode<T> inFront = pointer.getNext();
				pointer.setData(inFront.getData());
				pointer.setNext(inFront.getNext());
			}
		}
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Object[size()];
		SingleLinkedListNode<T> aux = this.head;
		for (int index = 0; !aux.isNIL(); index++) {
			array[index] = aux.getData();
			aux = aux.getNext();
		}
		return array;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

}
