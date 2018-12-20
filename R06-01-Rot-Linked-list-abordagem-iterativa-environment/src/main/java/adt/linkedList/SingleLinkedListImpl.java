package adt.linkedList;

import java.util.ArrayList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return this.head.isNIL();
	}

	/**
	 * Percorre todos Nodes e incrementa o contador para cada Node percorrido.
	 */
	@Override
	public int size() {

		int size = 0;
		SingleLinkedListNode<T> auxHead = getHead();

		while (!auxHead.isNIL()) {
			size++;
			auxHead = auxHead.next;
		}

		return size;
	}

	/**
	 * Percorre todos Nodes checando se o Node eh igual ao elemento, se for, retorna
	 * o elemento, se nao, retorna nulo.
	 */
	@Override
	public T search(T element) {
		SingleLinkedListNode<T> auxHead = getHead();
		while (!auxHead.isNIL() && auxHead.data != element) {
			auxHead = auxHead.next;
		}

		return auxHead.data;
	}

	/**
	 * Usando um Node auxiliar, chegamos ao fim da lista, e o entao ultimo node
	 * passa a apontar para o novo Node adicionado.
	 */
	@Override
	public void insert(T element) {
		SingleLinkedListNode<T> auxHead = getHead();

		if (getHead().isNIL()) {
			SingleLinkedListNode<T> newHead = new SingleLinkedListNode<T>(element, getHead());
			this.head = newHead;

		} else {
			while (!auxHead.next.isNIL()) {
				auxHead = auxHead.next;
			}
			SingleLinkedListNode<T> newNode = new SingleLinkedListNode<T>(element, auxHead.next);
			auxHead.next = newNode;

		}
	}

	/**
	 * Procura o Node a ser removido e faz o seu anterior apontar para seu
	 * posterior.
	 */
	@Override
	public void remove(T element) {
		if (this.head.data == element)
			this.head = getHead().next;

		else {
			SingleLinkedListNode<T> previous = new SingleLinkedListNode<T>();
			SingleLinkedListNode<T> auxHead = getHead();
			while (!auxHead.isNIL() && auxHead.data != element) {
				previous = auxHead;
				auxHead = auxHead.next;
			}
			if (!auxHead.isNIL()) {
				previous.next = auxHead.next;
			}

		}
	}

	/**
	 * Percorre todos os Nodes e adiciona o seus valores em um array.
	 */
	@Override
	public T[] toArray() {
		ArrayList<T> elements = new ArrayList<T>();
		SingleLinkedListNode<T> auxHead = getHead();
		while (!auxHead.isNIL()) {
			elements.add(auxHead.data);
			auxHead = auxHead.next;
		}

		return (T[]) elements.toArray();
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

}
