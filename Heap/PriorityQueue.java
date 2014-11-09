public class PriorityQueue<T extends Comparable<? super T>> implements
       PriorityQueueInterface<T>, Gradable<T> {
	
	private Heap<T> prioQ = new Heap<>();
    @Override
    public void insert(T item) {
        prioQ.add(item);
    }

    @Override
    public T findMin() {
       return prioQ.peek();
    }

    @Override
    public T deleteMin() {
        return prioQ.remove();
    }

    @Override
    public boolean isEmpty() {
        return prioQ.isEmpty();
    }

    @Override
    public void makeEmpty() {
        prioQ = null;
    }

    @Override
    public T[] toArray() {
        return prioQ.toArray();
    }


}
