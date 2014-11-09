public class Heap<T extends Comparable<? super T>> implements HeapInterface<T>,
       Gradable<T> {

	private static final int CAPACITY = 10;
	protected T[] heap = (T[]) new Comparable[CAPACITY];
	protected int size = 0;
	
    @Override
    public void add(T item) {
        if (item == null) {
        	throw new IllegalArgumentException("You cannot add a null");
        } if (size + 1 >= heap.length) {
        	resize();
        }
    	   heap[size + 1] = item;
        	size++;
        	int index = size;
        	while (index > 1 && heap[index].compareTo(heap[index / 2]) < 0) {
        	    swap(index, index / 2);
        	    index = index / 2;
        	}
        }
/**
 * Swaps two elements in an array.
 * @param indx1
 * @param indx2
 */
    private void swap(int indx1, int indx2) {
    	T temp = heap[indx1];
    	heap[indx1] = heap[indx2];
    	heap[indx2] = temp;
    }
/**
 * Resizes an array if it is full
 */
    private void resize() {
    	int newLength = 2 * heap.length;
    	T[] newArray = (T[]) new Comparable[newLength];
    	for (int i = 0; i < heap.length; i++) {
    		newArray[i] = heap[i];
    	}
    	heap = newArray;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public T peek() {
    	return heap[1];
    }

    @Override
    public T remove() {
    	T temp = heap[1];
      heap[1] = heap[size];
      heap[size] = null;
      size--;
      bubbleDown(1);
      return temp;
    }
/**
 * push an element down the heap to satisfy the heap condition.
 * @param index
 */
    private void bubbleDown(int index) {
    	while (hasLeft(index)) {
    		T smallerKid = heap[2 * index];
    		int tempIndex = 2 * index;
    		if (hasRight(index) && smallerKid.compareTo(heap[2 * index + 1]) > 0) {
    			smallerKid = heap[2 * index + 1];
    			tempIndex = 2 * index + 1; // keep the index of the smaller kid
    		}
    		if (smallerKid.compareTo(heap[index]) < 0) {
    		    swap(index, tempIndex);
    		    index = tempIndex; // update the index
    		} else {
    			return;
    		}
    	}
    }
/**
 * Checks if a node has left child.
 * @param index
 * @return
 */
    private boolean hasLeft(int index) {
    	return 2 * index < size;
    }
/**
 * checks if a node has a right child
 * @param index
 * @return
 */
    private boolean hasRight(int index) {
    	return 2 * index + 1 < size;
    }
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public T[] toArray() {
        return heap;
    }
}
