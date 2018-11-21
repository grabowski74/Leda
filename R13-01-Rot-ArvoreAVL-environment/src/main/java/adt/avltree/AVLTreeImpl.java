package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

	private static final int ZERO = 0;
	private static final int UNBALANCED_RIGHT = 1;
	private static final int UNBALANCED_LEFT = -1;

	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);
		node = super.remove(node);
		rebalanceUp(node);
	}

	@Override
	public void insert(T element) {
		super.insert(element);
		BSTNode<T> node = search(element);
		rebalanceUp(node);
	}

	// AUXILIARY
	/**
	 * Calculates the balance of the node. Returns a positive integer if
	 * unbalanced to right or a negative integer if unbalanced to the left, zero
	 * otherwise.
	 */
	protected int calculateBalance(BSTNode<T> node) {
		return node == null || node.isEmpty() ? 0 : height(getRight(node)) - height(getLeft(node));
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		int balance = calculateBalance(node);
		if (balance < UNBALANCED_LEFT) {
			if (calculateBalance(getLeft(node)) > ZERO) {
				leftRotation(getLeft(node));
			}
			rightRotation(node);
		} else if (balance > UNBALANCED_RIGHT) {
			if (calculateBalance(getRight(node)) < ZERO) {
				rightRotation(getRight(node));
			}
			leftRotation(node);
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		if (node != null) {
			rebalance(node);
			rebalanceUp(getParent(node));
		}
	}

	// AUXILIARY
	protected void leftRotation(BSTNode<T> node) {
		BSTNode<T> newNode = Util.leftRotation(node);
		if (newNode.getParent() == null) {
			root = newNode;
		}
	}

	// AUXILIARY
	protected void rightRotation(BSTNode<T> node) {
		BSTNode<T> newNode = Util.rightRotation(node);
		if (newNode.getParent() == null) {
			root = newNode;
		}
	}
}