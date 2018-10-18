package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends
        AbstractHashtableOpenAddress<T> {

   

    public HashtableOpenAddressLinearProbingImpl(int size,
                                                 HashFunctionClosedAddressMethod method) {
        super((size < 0) ? 0 : size);
        this.hashFunction = new HashFunctionLinearProbing<T>(size, method);
        this.initiateInternalTable(size);
    }

    @Override
    public void insert(T element) {
        if (element != null) {
            if (!isFull()) {
                int probe = this.probeOf(element);

                if (this.containsElement(probe)) {
                    int hash = ((HashFunctionLinearProbing) this.hashFunction).hash(element, probe);
                    this.table[hash] = element;
                } else {
                    probe = 0;
                    int hash = ((HashFunctionLinearProbing) this.hashFunction).hash(element, probe);
                    while (!canPutElement(hash, element)) {
                        hash = ((HashFunctionLinearProbing) this.hashFunction).hash(element, ++probe);
                        this.COLLISIONS++;
                    }

                    this.table[hash] = element;
                    this.elements++;
                }
            } else {
                throw new HashtableOverflowException();
            }
        }
    }

    @Override
    public void remove(T element) {
        if (element != null && !isEmpty()) {
            int probe = this.probeOf(element);
            int index = this.indexOf(element, probe);

            if (this.containsElement(index)) {
                this.COLLISIONS -= probe;
                this.table[index] = deletedElement;
                this.elements--;
            }
        }
    }

    @Override
    public T search(T element) {
        return (this.containsElement(element)) ? element : null;
    }

    @Override
    public int indexOf(T element) {
        int probe = this.probeOf(element);
        return this.indexOf(element, probe);
    }

    
    private int indexOf(T element, int probe) {
        return (probe == -1) ? -1 :
                ((HashFunctionLinearProbing) this.hashFunction).hash(element, probe);
    }

    
    private int probeOf(T element) {
        if (element != null && !isEmpty()) {
            int probe = 0;
            int hash = ((HashFunctionLinearProbing) this.hashFunction).hash(element, probe);

            while (!resetedProbe(element, probe)
                    && this.table[hash] != null
                    && this.table[hash].hashCode() != element.hashCode()) {
                hash = ((HashFunctionLinearProbing) this.hashFunction).hash(element, ++probe);
            }

            return (!resetedProbe(element, probe) && this.table[hash] != null
                    && this.table[hash].hashCode() == element.hashCode()) ? probe : -1;
        } else {
            return -1;
        }
    }

    
    private boolean canPutElement(int hash, T element) {
        return this.table[hash] == null
                || this.table[hash].equals(deletedElement)
                || this.table[hash].hashCode() == element.hashCode();
    }

   
    private boolean resetedProbe(T element, int probe) {
        int initialHash = ((HashFunctionLinearProbing) this.hashFunction).hash(element, 0);
        int hash = ((HashFunctionLinearProbing) this.hashFunction).hash(element, probe);
        return (probe != 0 && hash == initialHash);
    }


    
    private boolean containsElement(T element) {
        return this.containsElement(this.indexOf(element));
    }

    
    private boolean containsElement(int index) {
        return index != -1;
    }

}