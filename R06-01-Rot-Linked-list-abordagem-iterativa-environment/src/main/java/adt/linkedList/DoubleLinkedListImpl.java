package adt.linkedList;

public class DoubleLinkedListImpl<T extends Comparable<T>> extends SingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;

	/**
	 * Inicia a lista, assumindo que last == head.
	 */
	public DoubleLinkedListImpl() {
		this.head = new DoubleLinkedListNode<>();

		if (this.head instanceof DoubleLinkedListNode<?>) {
			this.last = (DoubleLinkedListNode<T>) this.head;
		}
	}

	/**
	 * Sobrescreve o metodo insert para lidar com os elementos da Double Linked List
	 * como Previous e Last, que nao existia na classe mae.
	 */
	@Override
	public void insert(T element) {

		if (getLast().isNIL()) {
			DoubleLinkedListNode<T> nilNode = new DoubleLinkedListNode<T>();
			DoubleLinkedListNode<T> newLast = new DoubleLinkedListNode<T>(element, nilNode, getLast());
			nilNode.setPrevious(newLast);
			getLast().setNext(newLast);

			setHead(newLast);
			setLast(newLast);
		} else {
			DoubleLinkedListNode<T> newLast = new DoubleLinkedListNode<T>(element,
					(DoubleLinkedListNode<T>) getLast().getNext(), getLast());
			((DoubleLinkedListNode<T>) getLast().getNext()).setPrevious(newLast);
			getLast().setNext(newLast);
			setLast(newLast);

		}

	}

	/**
	 * Caso a lista seja vazia, cria-se um novo node Nil, e um novo Node que aponta
	 * para a antiga Head (next) e para o Node Nil criado (previous). Seta o next no
	 * Nil criado como o Node a ser adicionado. Seta o previous do Head atual como o
	 * novo Node a ser adicionado. Atualiza o Head e o Last para o novo Node.
	 * 
	 * Caso a lista ja tenha elementos, criamos um novo node que ira apontar para a
	 * Head atual e para o previous da head atual. Seta o Next do anterior ao Head
	 * atual como o novo node criado. Seta o Previous do head atual como novo node
	 * criado. E por fim atualiza do head para o novo node.
	 * 
	 */
	@Override
	public void insertFirst(T element) {

		if (getHead().isNIL()) {
			DoubleLinkedListNode<T> nilNode = new DoubleLinkedListNode<T>();
			DoubleLinkedListNode<T> newHead = new DoubleLinkedListNode<T>(element, (DoubleLinkedListNode<T>) getHead(),
					nilNode);
			nilNode.setNext(newHead);
			((DoubleLinkedListNode<T>) getHead()).setPrevious(newHead);

			setLast(newHead);
			setHead(newHead);
		} else {
			DoubleLinkedListNode<T> newHead = new DoubleLinkedListNode<T>(element, (DoubleLinkedListNode<T>) getHead(),
					((DoubleLinkedListNode<T>) getHead()).getPrevious());
			((DoubleLinkedListNode<T>) getHead()).getPrevious().setNext(newHead);
			((DoubleLinkedListNode<T>) getHead()).setPrevious(newHead);
			setHead(newHead);

		}
	}

	/**
	 * Percorre a lista do comeco ao meio e do final ao meio (das pontas para o
	 * meio) procurando o elemento, podendo ser encontrado pelo apontador que parte
	 * de Head ou pelo apontador que parte de Last.
	 * 
	 */
	@Override
	public void remove(T element) {
		if (getHead().getData() == element)
			removeFirst();
		if (getLast().getData() == element)
			removeLast();

		SingleLinkedListNode<T> auxHead = getHead();
		DoubleLinkedListNode<T> auxLast = getLast();

		while (auxHead != auxLast && auxHead.getNext() != auxLast && auxHead.getData() != element
				&& auxLast.getData() != element) {
			auxHead = auxHead.getNext();
			auxLast = auxLast.getPrevious();
		}

		if (auxHead.getData() == element) {
			((DoubleLinkedListNode<T>) auxHead).getPrevious().setNext(auxHead.getNext());
			((DoubleLinkedListNode<T>) auxHead.getNext())
					.setPrevious(((DoubleLinkedListNode<T>) auxHead).getPrevious());
		}
		if (auxLast.getData() == element) {
			auxLast.getPrevious().setNext(auxLast.getNext());
			((DoubleLinkedListNode<T>) auxLast.getNext()).setPrevious(auxLast.getPrevious());
		}

	}

	/**
	 * Se head nao for NIL, Head agora eh o next de Head. Se o novo Head for NIL,
	 * last tambem vai ser head.
	 * 
	 * Eh criado um node NIL que vai ser o Previous do novo Head.
	 */
	@Override
	public void removeFirst() {
		if (!getHead().isNIL()) {
			setHead(getHead().getNext());
			if (getHead().isNIL())
				setLast((DoubleLinkedListNode<T>) getHead());
			DoubleLinkedListNode<T> nilNode = new DoubleLinkedListNode<T>();
			((DoubleLinkedListNode<T>) getHead()).setPrevious(nilNode);
		}
	}

	/**
	 * Se last nao for NIL, Last agora eh o previous de Last. Se o novo Last for
	 * NIL, head agora tambem vai ser Last.
	 * 
	 * Eh criado um node NIL que vai ser o next do novo Last.
	 */
	@Override
	public void removeLast() {
		if (!getLast().isNIL()) {
			setLast(getLast().getPrevious());
			if (getLast().isNIL())
				setHead(getLast());
			DoubleLinkedListNode<T> nilNode = new DoubleLinkedListNode<T>();
			getLast().setNext(nilNode);
		}
	}

	/**
	 * Usando dois apontadores, percorremos a lista das pontas para o meio,
	 * reduzindo o tempo de busca pela metade.
	 */
	@Override
	public T search(T element) {
		SingleLinkedListNode<T> auxHead = getHead();
		DoubleLinkedListNode<T> auxLast = getLast();
		T result = null;

		while (auxHead != auxLast && auxHead.getNext() != auxLast && auxHead.getData() != element
				&& auxLast.getData() != element) {
			auxHead = auxHead.getNext();
			auxLast = auxLast.getPrevious();
		}

		if (auxHead.getData() == element)
			result = auxHead.getData();
		if (auxLast.getData() == element)
			result = auxLast.getData();

		return result;

	}

	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}
	
	//MERGESORT LINKED LIST
	
	public DoubleLinkedListNode<T> mergeSort(DoubleLinkedListNode<T> h) {
		if(h == null || h.next == null) {
			return h;
		}
		
		DoubleLinkedListNode<T> middle = getMiddle(h);
		DoubleLinkedListNode<T> nextOfMiddle = (DoubleLinkedListNode<T>) middle.next;
		
		middle.next = null;
		
		DoubleLinkedListNode<T> left = mergeSort(h);
		DoubleLinkedListNode<T> right = mergeSort(nextOfMiddle);
		
		DoubleLinkedListNode<T> sortedList = sortedMerge(left, right);
		
		return sortedList;
	}

	private DoubleLinkedListNode<T> sortedMerge(DoubleLinkedListNode<T> left, DoubleLinkedListNode<T> right) {
		DoubleLinkedListNode<T> result = null;
		
		if(left == null) {
			return right; 
		}
		if(right == null) {
			return left;
		}
		
		if(left.getData().compareTo(right.getData()) < 0) {
			result = left;
			result.next = sortedMerge((DoubleLinkedListNode<T>) left.next, right);
		} else {
			result = right;
			result.next = sortedMerge(left, (DoubleLinkedListNode<T>) right.next);
		}
		return result;
	}

	private DoubleLinkedListNode<T> getMiddle(DoubleLinkedListNode<T> h) {
		if(h == null) {
			return h;
		}
		
		DoubleLinkedListNode<T> fastPtr = (DoubleLinkedListNode<T>) h.next;
		DoubleLinkedListNode<T> slowPtr = (DoubleLinkedListNode<T>) h;
		
		while(fastPtr != null) {
			fastPtr = (DoubleLinkedListNode<T>) fastPtr.next;
			if(fastPtr != null) {
				slowPtr = (DoubleLinkedListNode<T>) slowPtr.next;
				fastPtr = (DoubleLinkedListNode<T>) fastPtr.next;
			}
		}
		
		return slowPtr;
	}
}
