package adt.bst;

import adt.bt.BTNode;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	private static final int GREATER = 1;

	private static final int LESSER = -1;

	private static final int PRE_ORDER = -1;

	private static final int ORDER = 0;

	private static final int POST_ORDER = 1;

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root) - 1;
	}

	protected int height(BSTNode<T> nodeAt) {
		int height = 0;
		if (!nodeAt.isEmpty()) {
			int leftHeight = height(getLeft(nodeAt));
			int rightHeight = height(getRight(nodeAt));
			height = 1 + Math.max(leftHeight, rightHeight);
		}
		return height;
	}

	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> result = new BSTNode<>();
		if (!isNull(element))
			result = search(this.root, element);
		return result;
	}

	private BSTNode<T> search(BSTNode<T> nodeAt, T element) {
		BSTNode<T> result = null;
		if (!nodeAt.isEmpty()) {
			int comparation = nodeAt.getData().compareTo(element);
			if (comparation == GREATER)
				result = search(getLeft(nodeAt), element);
			else if (comparation == LESSER)
				result = search(getRight(nodeAt), element);
		}
		if (result == null) {
			result = nodeAt;
		}
		return result;
	}

	@Override
	public void insert(T element) {
		if (!isNull(element))
			insert(this.root, element);
	}

	private void insert(BSTNode<T> nodeAt, T element) {
		if (nodeAt.isEmpty()) {
			nodeAt.setData(element);
			nodeAt.setLeft(new BSTNode<T>());
			nodeAt.getLeft().setParent(nodeAt);
			nodeAt.setRight(new BSTNode<T>());
			nodeAt.getRight().setParent(nodeAt);
		} else {
			int comparation = nodeAt.getData().compareTo(element);
			if (comparation == GREATER)
				insert(getLeft(nodeAt), element);
			else if (comparation == LESSER)
				insert(getRight(nodeAt), element);
		}
	}

	@Override
	public BSTNode<T> maximum() {
		return this.isEmpty() ? null : maximum(this.root);
	}

	private BSTNode<T> maximum(BSTNode<T> nodeAt) {
		BSTNode<T> result = null;
		if (nodeAt.isLeaf() || nodeAt.getRight().isEmpty())
			result = nodeAt;
		else if (!nodeAt.getRight().isEmpty())
			result = maximum(getRight(nodeAt));
		return result;
	}

	@Override
	public BSTNode<T> minimum() {
		return this.isEmpty() ? null : minimum(this.root);
	}

	private BSTNode<T> minimum(BSTNode<T> nodeAt) {
		BSTNode<T> result = null;
		if (nodeAt.isLeaf() || nodeAt.getLeft().isEmpty())
			result = nodeAt;
		else if (!nodeAt.getLeft().isEmpty())
			result = minimum(getLeft(nodeAt));
		return result;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> result = null;
		BSTNode<T> elementNode = search(element);
		if (!isNull(element) && !elementNode.isEmpty()) {
			result = sucessor(elementNode, elementNode);
		}
		return result;
	}

	private BSTNode<T> sucessor(BSTNode<T> nodeAt, BSTNode<T> nodeFrom) {
		BSTNode<T> result = null;
		if (!nodeAt.isEmpty()) {
			boolean isRightEligible = !nodeAt.getRight().isEmpty()
					&& !nodeAt.getRight().getData().equals(nodeFrom.getData());
			if (isRightEligible) {
				result = minimum(getRight(nodeAt));
			} else if (nodeAt.getParent() != null) {
				int comparation = nodeAt.getParent().getData().compareTo(nodeAt.getData());
				if (comparation == GREATER)
					result = getParent(nodeAt);
				else
					result = sucessor(getParent(nodeAt), nodeAt);
			}
		}
		return result;
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> result = null;
		BSTNode<T> elementNode = search(element);
		if (!isNull(element) && !elementNode.isEmpty()) {
			result = predecessor(elementNode, elementNode);
		}
		return result;
	}

	private BSTNode<T> predecessor(BSTNode<T> nodeAt, BSTNode<T> from) {
		BSTNode<T> result = null;
		if (!nodeAt.isEmpty()) {
			boolean isLeftEmpty = nodeAt.getLeft().isEmpty();
			boolean isLeftEligible = !isLeftEmpty && !nodeAt.getLeft().getData().equals(from.getData());
			if (isLeftEligible) {
				result = maximum(getLeft(nodeAt));
			} else if (nodeAt.getParent() != null) {
				int comparation = nodeAt.getParent().getData().compareTo(nodeAt.getData());
				if (comparation == LESSER)
					result = getParent(nodeAt);
				else
					result = predecessor(getParent(nodeAt), nodeAt);
			}
		}
		return result;
	}

	@Override
	public void remove(T element) {
		BSTNode<T> nodeToRemove = search(element);
		if (!isNull(element) && !isNull(nodeToRemove))
			remove(nodeToRemove);
	}

	protected BSTNode<T> remove(BSTNode<T> nodeAt) {
		BSTNode<T> removed = nodeAt;
		if (!nodeAt.isEmpty()) {
			if (nodeAt.getLeft().isEmpty() || nodeAt.getRight().isEmpty()) {
				BSTNode<T> newThis = getLeft(nodeAt);
				if (newThis.isEmpty())
					newThis = getRight(nodeAt);
				nodeAt.setData(newThis.getData());
				nodeAt.setLeft(newThis.getLeft());
				nodeAt.setRight(newThis.getRight());
				if (!isNull(nodeAt.getLeft())) {
					nodeAt.getLeft().setParent(nodeAt);
				}
				if (!isNull(nodeAt.getRight())) {
					nodeAt.getRight().setParent(nodeAt);
				}
				
			} else {
				BSTNode<T> newThis = sucessor(nodeAt.getData());
				nodeAt.setData(newThis.getData());
				removed = remove(newThis);
			}
		}
		return removed;
	}

	@Override
	public T[] preOrder() {
		return walkByTree(root, PRE_ORDER);
	}

	@Override
	public T[] order() {
		return walkByTree(root, ORDER);
	}

	@Override
	public T[] postOrder() {
		return walkByTree(root, POST_ORDER);
	}

	/**
	 * Walks along the tree, making an array in the type root + left + root +
	 * right + root where the actual root can assume one of the 3 possible
	 * positions, depending on the @param rootPosition
	 */
	@SuppressWarnings("unchecked")
	private T[] walkByTree(BSTNode<T> nodeAt, int rootPosition) {
		T[] array = (T[]) new Comparable[0];
		if (!nodeAt.isEmpty()) {
			T[] leftTree = walkByTree(getLeft(nodeAt), rootPosition);
			T[] rightTree = walkByTree(getRight(nodeAt), rootPosition);
			array = (T[]) new Comparable[1 + leftTree.length + rightTree.length];

			int start = 0;
			if (rootPosition == PRE_ORDER)
				array[start++] = nodeAt.getData();
			// Copying elements from left tree
			for (T e : leftTree) {
				array[start++] = e;
			}
			if (rootPosition == ORDER)
				array[start++] = nodeAt.getData();
			// Copying elements from right tree
			for (T e : rightTree) {
				array[start++] = e;
			}
			if (rootPosition == POST_ORDER)
				array[start++] = nodeAt.getData();

		}
		return array;
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}

	private boolean isNull(Object o) {
		return o == null;
	}

	protected BSTNode<T> getParent(BSTNode<T> nodeAt) {
		return (BSTNode<T>) nodeAt.getParent();
	}

	protected BSTNode<T> getLeft(BTNode<T> node) {
		return (BSTNode<T>) node.getLeft();
	}

	protected BSTNode<T> getRight(BTNode<T> node) {
		return (BSTNode<T>) node.getRight();
	}
}