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
		int size = 0;
		if (!isEmpty()) {
			return 1 + this.next.size();
		}
		return size;
	}

	@Override
	public T search(T element) {
		T retorno = null;
		if (!isEmpty()) {
			if (this.data.equals(element)) {
				retorno = element;
			} else {
				retorno = this.next.search(element);
			}
		}
		return retorno;
	}

	@Override
	public void insert(T element) {
		if (isEmpty()) {
			this.data = element;
			this.next = new RecursiveSingleLinkedListImpl<>();
		} else {
			this.next.insert(element);
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty()) {
			if (this.data.equals(element)) {
				this.data = this.next.getData();
				if (this.next != null) {
					this.next = this.next.getNext();
				}
			} else {
				if (this.next != null) {
					this.next.remove(element);
				}
			}
		}
	}

	@Override
	public T[] toArray() {
		T[] array = (T[]) new Object[this.size()];
		if (!isEmpty())
			array = (T[]) new Object[this.size()];
		return toArrayRec(array, 0);
	}

	public T[] toArrayRec(T[] array, int i) {
		if (!this.isEmpty()) {
			array[i] = this.data;
			this.next.toArrayRec(array, ++i);
		}
		return array;
	}

	public String toString() {
		String retorno = "[";
		if (data != null) {
			retorno += data.toString();
			RecursiveSingleLinkedListImpl<T> aux = next;
			while (aux.getData() != null) {
				retorno += ", " + aux.getData().toString();
				aux = aux.getNext();
			}
		}
		return retorno + "]";
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
