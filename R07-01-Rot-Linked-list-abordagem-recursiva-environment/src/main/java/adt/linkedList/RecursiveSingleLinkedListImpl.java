package adt.linkedList;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {
	
	protected T data;
	protected RecursiveSingleLinkedListImpl<T> next;

	public RecursiveSingleLinkedListImpl() {
	}

	public RecursiveSingleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next) {
		this.data = data;
		this.next = next;
	}

	@Override
	public boolean isEmpty() {
		return this.data == null;
	}

	@Override
	public int size() {
		return (isEmpty()) ? 0 : 1 + next.size();
	}

	@Override
	public T search(T element) {
		T searchResult = null;
		if (element != null && !isEmpty()) {
			if (this.data.equals(element)) {
				searchResult = this.data;
			} else {
				searchResult = this.next.search(element);
			}
		}
		return searchResult;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			if (isEmpty()) {
				this.data = element;
				this.next = new RecursiveDoubleLinkedListImpl<>();
			} else {
				this.next.insert(element);
			}
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty() && element != null) {
			if (this.data.equals(element)) {
				this.data = this.next.getData();
				this.next = this.next.getNext();
			} else {
				this.next.remove(element);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] toArray() {
		T[] thisArray = (T[]) new Object[0];
		if (!isEmpty()) {
			T[] subArray = this.next.toArray();
			thisArray = (T[]) new Object[1 + subArray.length];
			thisArray[0] = this.data;
			for (int pointer = 1; pointer < thisArray.length; pointer++)
				thisArray[pointer] = subArray[pointer - 1];
		}
		return thisArray;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RecursiveSingleLinkedListImpl<T> getNext() {
		return next;
	}

	public void setNext(RecursiveSingleLinkedListImpl<T> next) {
		this.next = next;
	}
}