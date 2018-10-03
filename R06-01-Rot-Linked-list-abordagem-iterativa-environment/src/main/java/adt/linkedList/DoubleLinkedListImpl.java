package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;

	public DoubleLinkedListImpl() {
		this.head = this.last = new DoubleLinkedListNode<T>();
	}

	@Override
	public void insert(T element) {
		if (!isNull(element)) {
			if (isEmpty()) {
				DoubleLinkedListNode<T> newHead = newNode(element, last, null);
				newHead.setPrevious(newNode(null, newHead, null));
				setHead(newHead);
				last.setPrevious(newHead);
			} else {
				DoubleLinkedListNode<T> newLast = newNode(null, null, last);
				last.setData(element);
				last.setNext(newLast);
				last = newLast;
			}
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty() && !isNull(element)) {
			DoubleLinkedListNode<T> aux = getHead();
			while (!aux.isNIL() && !equalData(element, aux)) {
				aux = getNext(aux);
			}
			if (!aux.isNIL() && equalData(element, aux)) {
				DoubleLinkedListNode<T> newNext = getNext(getNext(aux));
				aux.setData(aux.getNext().getData());
				aux.setNext(newNext);
				if (newNext != null)
					newNext.setPrevious(aux);
			}
		}
	}

	@Override
	public void insertFirst(T element) {
		if (!isNull(element)) {
			DoubleLinkedListNode<T> newHead = newNode(element, getHead(), getHead().getPrevious());
			newHead.getPrevious().setNext(newHead);
			getHead().setPrevious(newHead);
			setHead(newHead);
		}
	}

	@Override
	public void removeFirst() {
		if (!isEmpty()) {
			getHead().setData(null);
			getHead().setPrevious(null);
			setHead(getHead().getNext());
		}
	}

	@Override
	public void removeLast() {
		if (!isEmpty()) {
			DoubleLinkedListNode<T> preLast = last.getPrevious();
			preLast.setData(null);
			preLast.setNext(null);
			last = preLast;
		}
	}

	private boolean equalData(T element, DoubleLinkedListNode<T> aux) {
		return aux.getData().equals(element);
	}

	private boolean isNull(Object o) {
		return o == null;
	}

	private DoubleLinkedListNode<T> newNode(T data, DoubleLinkedListNode<T> next, DoubleLinkedListNode<T> prev) {
		return new DoubleLinkedListNode<T>(data, next, prev);
	}

	@Override
	public DoubleLinkedListNode<T> getHead() {
		return (DoubleLinkedListNode<T>) super.getHead();
	}
	
	private DoubleLinkedListNode<T> getNext(DoubleLinkedListNode<T> node) {
		return (DoubleLinkedListNode<T>) node.getNext();
	}
}
