package adt.linkedList;

public class RecursiveDoubleLinkedListImpl<T> extends RecursiveSingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected RecursiveDoubleLinkedListImpl<T> previous;

	public RecursiveDoubleLinkedListImpl() {

	}

	public RecursiveDoubleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next,
			RecursiveDoubleLinkedListImpl<T> previous) {
		super(data, next);
		this.previous = previous;
	}

	@Override
	public void insert(T element) {
		RecursiveDoubleLinkedListImpl<T> novo = new RecursiveDoubleLinkedListImpl<>(element,
				new RecursiveDoubleLinkedListImpl<>(), new RecursiveDoubleLinkedListImpl<>());
		if (isEmpty()) {
			this.data = element;
			this.next = novo.getNext();
			this.previous = novo.getPrevious();
		} else if (this.next.isEmpty()) {
			this.setNext(novo);
			novo.setPrevious(this);
		} else {
			this.next.insert(element);
		}
	}

	@Override
	public void insertFirst(T element) {
		RecursiveDoubleLinkedListImpl<T> primeiro = new RecursiveDoubleLinkedListImpl<>();
		if (isEmpty()) {
			this.setData(element);
			this.setNext(new RecursiveDoubleLinkedListImpl<>());
			this.setPrevious(new RecursiveDoubleLinkedListImpl<>());
		} else {
			primeiro.setNext(getNext());
			((RecursiveDoubleLinkedListImpl<T>) getNext()).setPrevious(primeiro);
			primeiro.setPrevious(this);
			this.setNext(primeiro);

			primeiro.setData(this.getData());
			this.data = element;
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty()) {
			if (this.data.equals(element)) {
				if (this.size() == 1) {
					this.data = null;
					this.next = new RecursiveSingleLinkedListImpl<>();
					this.previous = new RecursiveDoubleLinkedListImpl<>();
				} else {
					RecursiveDoubleLinkedListImpl<T> anterior = this.getPrevious();
					RecursiveDoubleLinkedListImpl<T> posterior = (RecursiveDoubleLinkedListImpl<T>) this.getNext();
					T aux = null;
					if (anterior.isEmpty()) {
						aux = posterior.getData();
						this.setNext(posterior.getNext());
						((RecursiveDoubleLinkedListImpl<T>) posterior.getNext()).setPrevious(this);
						this.setData(aux);
					} else if (posterior.isEmpty()) {
						aux = anterior.getData();
						anterior.setNext(posterior);
						posterior.setPrevious(anterior);
					} else {
						anterior.setNext(posterior);
						posterior.setPrevious(anterior);
					}
				}
			} else {
				this.next.remove(element);
			}
		}
	}

	@Override
	public void removeFirst() {
		if (!isEmpty()) {
			if (this.size() == 1) {
				this.data = null;
				this.next = new RecursiveDoubleLinkedListImpl<>();
				this.previous = new RecursiveDoubleLinkedListImpl<>();
			} else {
				this.data = this.next.getData();
				this.next = this.next.getNext();
				this.setPrevious(new RecursiveDoubleLinkedListImpl<>());
			}
		}
	}

	@Override
	public void removeLast() {
		if (!isEmpty()) {
			if (size() == 1) {
				this.data = null;
				this.next = new RecursiveDoubleLinkedListImpl<>();
				this.previous = new RecursiveDoubleLinkedListImpl<>();
			} else {
				if (this.next.isEmpty()) {
					this.getPrevious().setNext(new RecursiveDoubleLinkedListImpl<>());
					this.previous = null;
				} else {
					((RecursiveDoubleLinkedListImpl<T>) this.next).removeLast();
				}
			}
		}
	}

	public RecursiveDoubleLinkedListImpl<T> getPrevious() {
		return previous;
	}

	public void setPrevious(RecursiveDoubleLinkedListImpl<T> previous) {
		this.previous = previous;
	}

}
