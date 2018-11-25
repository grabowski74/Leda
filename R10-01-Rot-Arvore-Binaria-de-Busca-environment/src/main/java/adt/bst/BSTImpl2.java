package adt.bst;

import adt.bt.BTNode;

public class BSTImpl2<T extends Comparable<T>> implements BST<T> {
	
	protected BSTNode<T> root;
	
	public BSTImpl2() {
		root = new BSTNode<T>();
	}

	@Override
	public BTNode<T> getRoot() {
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
	
	protected int height(BSTNode<T> anyNode) {
		int height = 0;
		if(! anyNode.isEmpty()) {
			int leftHeight = height(getLeft(anyNode));
			int rightHeight = height(getRight(anyNode));
			height = 1 + Math.max(leftHeight, rightHeight);
		}
		return height;
	}

	@Override
	public BTNode<T> search(T elem) {
		BSTNode<T> result = new BSTNode<>();
		if(! isNull(elem)) {
			result = search(this.root, elem);
		}
		return result;
	}

	private BSTNode<T> search(BSTNode<T> anyNode, T elem) {
		BSTNode<T> result = null;
		if(! anyNode.isEmpty()) {
			int compare = anyNode.getData().compareTo(elem);
			if(compare == 1) {
				result = search(getLeft(anyNode), elem);
			} else if(compare == -1) {
				result = search(getRight(anyNode), elem);
			}
			if (result == null) {
				result = anyNode;
			}
		}
		return result;
	}

	@Override
	public void insert(T value) {
		if (!isNull(value)) {
			insert(this.root, value);
		}
		
	}

	private void insert(BSTNode<T> anyNode, T value) {
		if(anyNode.isEmpty()) {
			anyNode.setData(value);
			anyNode.setLeft(new BSTNode<T>());
			anyNode.getLeft().setParent(anyNode);
			anyNode.setRight(new BSTNode<T>());
			anyNode.getRight().setParent(anyNode);
		} else {
			int compare = anyNode.getData().compareTo(value);
			if(compare == 1) {
				insert(getLeft(anyNode), value);
			} else if (compare == -1) {
				insert(getRight(anyNode), value);
			}
		}
		
	}

	@Override
	public void remove(T key) {
		BSTNode<T> removeNode = (BSTNode<T>) search(key);
		if(! isNull(key) && ! isNull(removeNode)) {
			remove(removeNode);
		}
		
	}

	protected BSTNode<T> remove(BSTNode<T> anyNode) {
		BSTNode<T> removing = anyNode;
		if(! anyNode.isEmpty()) {
			if(anyNode.getLeft().isEmpty() || anyNode.getRight().isEmpty()) {
				BSTNode<T> newThis = getLeft(anyNode);
				if(newThis.isEmpty()) {
					newThis = getRight(anyNode);
				}
				anyNode.setData(newThis.getData());
				anyNode.setLeft(newThis.getLeft());
				anyNode.setRight(newThis.getRight());
				if(! isNull(anyNode.getLeft())) {
					anyNode.getLeft().setParent(anyNode);
				}
				if(! isNull(anyNode.getRight())) {
					anyNode.getRight().setParent(anyNode);
				}
				
			} else {
				BSTNode<T> newThis = sucessor(anyNode.getData());
				anyNode.setData(newThis.getData());
				removing = remove(newThis);
			}
		}
		return removing;	
	}

	@Override
	public T[] preOrder() {
		return walkByTree(root, -1);
	}

	@Override
	public T[] order() {
		return walkByTree(root, 0);
	}

	@Override
	public T[] postOrder() {
		return walkByTree(root, 1);
	}

	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> anyNode) {
		int result = 0;
		if(! anyNode.isEmpty()) {
			result = 1 + size((BSTNode<T>) anyNode.getLeft()) + size((BSTNode<T>) anyNode.getRight());
		}
		return result;
	}

	@Override
	public BSTNode<T> maximum() {
		return this.isEmpty() ? null : maximun(this.root);
	}

	private BSTNode<T> maximun(BSTNode<T> anyNode) {
		BSTNode<T> result = null;
		if(anyNode.isLeaf() || anyNode.getRight().isEmpty()) {
			result = anyNode;
		} else if(! anyNode.getRight().isEmpty()) {
			result = maximun(getRight(anyNode));
		}
		return result;
	}

	@Override
	public BSTNode<T> minimum() {
		return this.isEmpty() ? null : minimum(this.root);
	}

	private BSTNode<T> minimum(BSTNode<T> anyNode) {
		BSTNode<T> result = null;
		if(anyNode.isLeaf() || anyNode.getLeft().isEmpty()) {
			result = anyNode;
		} else if (! anyNode.getLeft().isEmpty()) {
			result = (minimum(getLeft(anyNode)));
		}
		return result;
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> result = null;
		BSTNode<T> anyNode = (BSTNode<T>) search(element);
		if(! isNull(element) && ! anyNode.isEmpty()) {
			result = sucessor(anyNode, anyNode);
		}
		return result;
	}

	private BSTNode<T> sucessor(BSTNode<T> anyNode, BSTNode<T> anotherNode) {
		BSTNode<T> result = null;
		if(! anyNode.isEmpty()) {
			boolean isRighter = ! anyNode.getRight().isEmpty() 
					&& ! anyNode.getRight().getData().equals(anotherNode.getData());
			if(isRighter) {
				result = minimum(getRight(anyNode));
			} else if (anyNode.getParent() != null) {
				int compare = anyNode.getParent().getData().compareTo(anyNode.getData());
				if(compare == 1) {
					result = getParent(anyNode);
				} else {
					result = sucessor(getParent(anyNode), anyNode);
				}
			}
		}
		return result;
	}


	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> result = null;
		BSTNode<T> anyNode = (BSTNode<T>) search(element);
		if(! isNull(element) && ! anyNode.isEmpty()) {
			result = predecessor(anyNode, anyNode);
		}
		return result;
	}
	
	private BSTNode<T> predecessor(BSTNode<T> anyNode, BSTNode<T> anotherNode) {
		BSTNode<T> result = null;
		if(! anyNode.isEmpty()) {
			boolean isLeftEmpty = anyNode.getLeft().isEmpty();
			boolean isLefter = !isLeftEmpty && !anyNode.getLeft().getData().equals(anotherNode.getData());
			if(isLefter) {
				result = maximun(getLeft(anyNode));
			} else if (anyNode.getParent() != null) {
				int compare = anyNode.getParent().getData().compareTo(anyNode.getData());
				if(compare == -1) {
					result = getParent(anyNode);
				} else {
					result = predecessor(getParent(anyNode), anyNode);
				}
			}
		}
		return result;
	}

	private BSTNode<T> getRight(BSTNode<T> anyNode) {
		return (BSTNode<T>) anyNode.getRight();
	}

	private BSTNode<T> getLeft(BSTNode<T> anyNode) {
		return (BSTNode<T>) anyNode.getLeft();
	}
	
	private BSTNode<T> getParent(BSTNode<T> anyNode) {
		return (BSTNode<T>) anyNode.getParent();
	}
	
	private boolean isNull(Object elem) {
		return elem == null;
	}
	
	@SuppressWarnings("unchecked")
	private T[] walkByTree(BSTNode<T> anyNode, int i) {
		T[] array = (T[]) new Comparable[0];
		if(! anyNode.isEmpty()) {
			T[] leftTree = walkByTree(getLeft(anyNode), i);
			T[] rightTree = walkByTree(getRight(anyNode), i);
			array = (T[]) new Comparable[ 1 + leftTree.length + rightTree.length];
			
			int start = 0;
			if(i == -1) {
				array[start++] = anyNode.getData();
			}
			for(T a : leftTree) {
				array[start++] = a;
			}
			if(i == 0) {
				array[start++] = anyNode.getData();
			}
			for(T a : rightTree) {
				array[start++] = a;
			}
			if(i == 1) {
				array[start++] = anyNode.getData();
			}
		}
		return array;
	}
	
}