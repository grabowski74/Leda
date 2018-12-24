package adt.rbtree;

import adt.bst.BSTImpl;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

	public RBTreeImpl() {
		this.root = new RBNode<T>();
	}

	protected int blackHeight() {
		return this.blackHeight((RBNode<T>) this.getRoot());
	}

	private int blackHeight(RBNode<T> node) {
		int exit = 0;
		if (node.isEmpty()) {
			exit = 1;
		} else {
			if (node.getColour().equals(Colour.BLACK)) {
				exit = 1;
			}
			int left = this.blackHeight((RBNode<T>) node.getLeft());
			int right = this.blackHeight((RBNode<T>) node.getRight());
			if (left >= right) {
				exit += left;
			} else {
				exit += right;
			}
		}
		return exit;

	}

	protected boolean verifyProperties() {
		boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
				&& verifyBlackHeight();

		return resp;
	}

	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed by
	 * the type Colour.
	 */
	private boolean verifyNodesColour() {
		return true; // already implemented
	}

	/**
	 * The colour of the root must be black.
	 */
	private boolean verifyRootColour() {
		return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
		// implemented
	}

	/**
	 * This is guaranteed by the constructor.
	 */
	private boolean verifyNILNodeColour() {
		return true; // already implemented
	}

	/**
	 * Verifies the property for all RED nodes: the children of a red node must be
	 * BLACK.
	 */
	private boolean verifyChildrenOfRedNodes() {
		return this.verifyChildrenOfRedNodes((RBNode<T>) this.getRoot());
	}

	private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
		boolean toReturn = true;
		;
		if (!node.isEmpty()) {
			if (node.getColour().equals(Colour.RED)) {
				if (!((RBNode<T>) node.getLeft()).getColour().equals(Colour.BLACK)
						|| !((RBNode<T>) node.getRight()).getColour().equals(Colour.BLACK)) {
					toReturn = false;
				}
			}
			toReturn = this.verifyChildrenOfRedNodes((RBNode<T>) node.getLeft())
					&& this.verifyChildrenOfRedNodes((RBNode<T>) node.getRight());
		}
		return toReturn;
	}

	/**
	 * Verifies the black-height property from the root. The method blackHeight
	 * returns an exception if the black heights are different.
	 */
	private boolean verifyBlackHeight() {
		int left = this.blackHeight((RBNode<T>) this.getRoot().getLeft());
		int right = this.blackHeight((RBNode<T>) this.getRoot().getRight());
		return left == right;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			this.insert((RBNode<T>) this.getRoot(), element);
		}
	}

	private void insert(RBNode<T> node, T element) {
		if (node.isEmpty()) {
			node.setData(element);
			RBNode<T> left = new RBNode<T>();
			RBNode<T> right = new RBNode<T>();
			left.setParent(node);
			right.setParent(node);
			node.setLeft(left);
			node.setRight(right);
			node.setColour(Colour.RED);
			this.fixUpCase1(node);
		} else {
			if (node.getData().compareTo(element) > 0) {
				this.insert((RBNode<T>) node.getLeft(), element);
			} else if (node.getData().compareTo(element) < 0) {
				this.insert((RBNode<T>) node.getRight(), element);
			}
		}
	}

	@Override
	public RBNode<T>[] rbPreOrder() {
		RBNode<T>[] toReturn = new RBNode[this.size()];
		if (!this.isEmpty()) {
			this.rbPreOrder((RBNode<T>) this.getRoot(), 0, toReturn);
		}
		return toReturn;
	}

	private int rbPreOrder(RBNode<T> node, int index, RBNode<T>[] array) {
		if (!node.isEmpty()) {
			array[index++] = node;
			index = this.rbPreOrder((RBNode<T>) node.getLeft(), index, array);
			index = this.rbPreOrder((RBNode<T>) node.getRight(), index, array);
		}
		return index;
	}

	// FIXUP methods
	protected void fixUpCase1(RBNode<T> node) {
		if (node.equals((RBNode<T>) this.root)) {
			node.setColour(Colour.BLACK);
		} else {
			fixUpCase2(node);
		}
	}

	protected void fixUpCase2(RBNode<T> node) {
		if (!((RBNode<T>) node.getParent()).getColour().equals(Colour.BLACK)) {
			fixUpCase3(node);
		}
	}

	private RBNode<T> tio(RBNode<T> node) {
		RBNode<T> exit;
		if (node.getParent().getParent().getLeft().equals(node.getParent())) {
			exit = (RBNode<T>) node.getParent().getParent().getRight();
		} else {
			exit = (RBNode<T>) node.getParent().getParent().getLeft();
		}
		return exit;
	}

	protected void fixUpCase3(RBNode<T> node) {
		RBNode<T> avo = (RBNode<T>) node.getParent().getParent();

		RBNode<T> tio = this.tio(node);

		if (tio.getColour().equals(Colour.RED)) {
			((RBNode<T>) node.getParent()).setColour(Colour.BLACK);
			tio.setColour(Colour.BLACK);
			avo.setColour(Colour.RED);
			fixUpCase1(avo);
		} else {
			fixUpCase4(node);
		}
	}

	protected void fixUpCase4(RBNode<T> node) {
		RBNode<T> next = node;

		if (!this.isLeftChild(node) && this.isLeftChild((RBNode<T>) node.getParent())) {
			this.leftRotation((RBNode<T>) node.getParent());
			next = (RBNode<T>) node.getLeft();
		} else if (this.isLeftChild(node) && !this.isLeftChild((RBNode<T>) node.getParent())) {
			this.rightRotation((RBNode<T>) node.getParent());
			next = (RBNode<T>) node.getRight();
		}

		fixUpCase5(next);
	}

	protected void fixUpCase5(RBNode<T> node) {
		((RBNode<T>) node.getParent()).setColour(Colour.BLACK);
		((RBNode<T>) node.getParent().getParent()).setColour(Colour.RED);
		if (this.isLeftChild(node)) {
			this.rightRotation((RBNode<T>) node.getParent().getParent());
		} else {
			this.leftRotation((RBNode<T>) node.getParent().getParent());
		}
	}

	private boolean isLeftChild(RBNode<T> node) {
		return node.getParent().getLeft().equals(node);
	}

	private void rightRotation(RBNode<T> node) {
		if (node != null && !node.isEmpty()) {

			RBNode<T> aux = (RBNode<T>) Util.rightRotation(node);
			if (node.equals(getRoot())) {
				this.root = aux;
			}
		}
	}

	private void leftRotation(RBNode<T> node) {
		if (node != null && !node.isEmpty()) {
			RBNode<T> aux = (RBNode<T>) Util.leftRotation(node);
			if (node.equals(getRoot())) {
				this.root = aux;
			}
		}
	}
}
