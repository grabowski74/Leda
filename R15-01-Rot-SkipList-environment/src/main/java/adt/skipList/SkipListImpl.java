package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int height;
	protected int maxHeight;

	protected boolean USE_MAX_HEIGHT_AS_HEIGHT;
	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		if (USE_MAX_HEIGHT_AS_HEIGHT) {
			this.height = maxHeight;
		} else {
			this.height = 1;
		}
		this.maxHeight = maxHeight;
		root = new SkipListNode<>(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode<>(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		// As long as the variable USE_MAX_AS_HEIGHT isn't set at the
		// construction, we can't do nothing about this.
		SkipListNode<T>[] forward = root.getForward();
		for (int descuebra = 0; descuebra < maxHeight; descuebra++) {
			forward[descuebra] = NIL;
		}
	}

	/**
	 * Metodo que gera uma altura aleatoria para ser atribuida a um novo no no
	 * metodo insert(int,V)
	 */
	private int randomLevel() {
		int randomLevel = 1;
		double random = Math.random();
		while (Math.random() <= PROBABILITY && randomLevel < maxHeight) {
			randomLevel = randomLevel + 1;
		}
		// ?????????????????????????????? //
		return randomLevel;
	}

	@Override
	public void insert(int key, T newValue, int height) {
		SkipListNode<T> node = new SkipListNode<T>(key, height, newValue);
		SkipListNode<T> search = search(key);
		if (search != null) { // Only update the value
			search.setValue(newValue);
			return;
		}
		SkipListNode<T> aux = root;
		while (height > 0) {
			SkipListNode<T> next = aux.getForward(height - 1);
			int nextKey = next.getKey();
			while (aux.getKey() < key && nextKey < key) {
				aux = next;
				next = aux.getForward(height - 1);
				nextKey = next.getKey();
			}
			height--;
			node.getForward()[height] = aux.getForward(height);
			aux.getForward()[height] = node;
		}
	}

	@Override
	public void remove(int key) {
		SkipListNode<T> node = search(key);
		if (node != null) {
			SkipListNode<T> aux = root;
			int height = node.height;
			while (height > 0) {
				SkipListNode<T> next = aux.getForward(height - 1);
				int nextKey = next.getKey();
				while (aux.getKey() < key && nextKey < key) {
					aux = next;
					next = aux.getForward(height - 1);
					nextKey = next.getKey();
				}
				height--;
				aux.getForward()[height] = node.getForward(height);
			}
		}
	}

	@Override
	public int height() {
		SkipListNode<T>[] forward = root.getForward();
		int h = forward.length - 1;
		if (USE_MAX_HEIGHT_AS_HEIGHT) {
			h = maxHeight;
		} else {
			for (; h >= 0 && isRootOrNil(forward[h]); h--)
				;
			h += 1;
		}
		return h;
	}

	@Override
	public SkipListNode<T> search(int key) {
		int level = height() - 1;
		SkipListNode<T> aux = root;
		while (level >= 0 && aux != null) {
			int keyAt = aux.getKey();
			int nextKey = aux.getForward(level).getKey();
			if (keyAt == key) {
				break;
			} else if (nextKey > key) {
				level--;
			} else {
				aux = aux.getForward(level);
			}
		}
		return aux == null || aux.getKey() != key ? null : aux;
	}

	@Override
	public int size() {
		int size = 0;
		SkipListNode<T> aux = root;
		while (aux != null && !isRootOrNil(aux.getForward(0))) {
			size += isRootOrNil(aux) ? 0 : 1;
			aux = aux.getForward(0);
		}
		return size;
	}

	/**
	 * Checks if the node is the root or NIL.
	 * 
	 * @return True if the node is the root or NIL
	 */
	private boolean isRootOrNil(SkipListNode<T> aux) {
		return aux.getKey() == Integer.MAX_VALUE || aux.getKey() == Integer.MAX_VALUE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SkipListNode<T>[] toArray() {
		int ROOT_AND_NIL = 2;
		SkipListNode<T>[] array = new SkipListNode[size() + ROOT_AND_NIL];
		SkipListNode<T> aux = root;
		int ptr = 0;
		while (aux != null) {
			array[ptr++] = aux;
			aux = aux.getForward(0);
		}
		return array;
	}
}