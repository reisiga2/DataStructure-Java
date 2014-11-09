/**
 * Doubly linked list implementation
 * @author Mostafa Reisi Gahrooei
 * @version 1.0
 */
import java.lang.IndexOutOfBoundsException;

public class DoublyLinkedList<T> implements LinkedListInterface<T> {
	private Node<T> head;
	private Node<T> tail;
	private int size;

    @Override
    public void addAtIndex(int index, T data) {
       boundCheck(index, size + 1);
       if (index == 0) {
    	   addToFront(data);
       } else if (index == size) {
    	   addToBack(data);
       } else {
    	   Node<T> newNode = new Node<>(data);
    	   Node<T> current = head;
           for (int i = 0; i < index - 1; i++) {
        	   current = current.getNext();
           }
           Node<T> temp = current.getNext();
           current.setNext(newNode);
           temp.setPrevious(newNode);
           newNode.setNext(temp);
           newNode.setPrevious(current);
           size++;
       }
    }

    @Override
    public T get(int index) {
    	boundCheck(index, size);
    	if (isEmpty()) {
    		return null;
    	}
    	Node<T> current = head;
    	for (int i = 0; i < index; i++) {
    		current = current.getNext();
    	}
    	return current.getData();
    }

    @Override
    public T removeAtIndex(int index) {
    	boundCheck(index, size);
    	if (index == 0) {
    		return removeFromFront();
    	} else if (index == size - 1) {
    		return removeFromBack();
    	} else {
    		Node<T> current = head;
    		for (int i = 0; i < index; i++) {
    			current = current.getNext();
    		}
    		T data = current.getData();
    		Node<T> prev = current.getPrevious();
    		Node<T> next = current.getNext();
    		prev.setNext(next);
    		next.setPrevious(prev);
    		size--;
    		return data;
     	}
    }

    @Override
    public void addToFront(T t) {
    	Node<T> newNode = new Node<>(t);
    	if(head == null) {
    		head = newNode;
    		tail = newNode;
    		size++;
    	} else {
        head.setPrevious(newNode);
        newNode.setNext(head);
        head = newNode;
        size++;
    	}
    }

    @Override
    public void addToBack(T t) {
        Node<T> newNode = new Node<>(t);
        if (head == null) {
        	head = newNode;
        	tail = newNode;
        	size++;
        } else {
        tail.setNext(newNode);
        newNode.setPrevious(tail);
        tail = newNode;
        size++;
        }
    }

    @Override
    public T removeFromFront() {
        if (isEmpty()) {
            return null;
        }
        Node<T> temp = head.getNext();
        T data = head.getData();
        if (size == 1) {
        	head = null;
        	tail = null;
        	size--;
        } else {
        	temp.setPrevious(null);
        	head = temp;
        	size--;
        }
        return data;
    }

    @Override
    public T removeFromBack() {
        if (isEmpty()) {
        	return null;
        }
        T data = tail.getData();
        Node<T> temp = tail.getPrevious();
        if(size == 1) {
        	head = null;
        	tail = null;
        	size--;
        } else {
            temp.setNext(null);
            tail = temp;
            size--;
        }
        return data;
    }

    @Override
    public Object[] toArray() {
        if(isEmpty()) {
        	return null;
        }
    	Object[] arr = new Object[size];
    	Node<T> current = head;
    	arr[0] = current.getData();
        for (int i = 1; i < size; i++) {
        	current = current.getNext();
        	arr[i] = current.getData();
        }
        return arr;
    }

    @Override
    public boolean isEmpty() {
          return (size == 0);
    }

    @Override
    public int size() {
          return size;
    }

    @Override
    public void clear() {
    	head = null;
    	tail = null;
    	size = 0;
    }

    private void boundCheck(int index, int size) {
    	if (index < 0 || index > size - 1) {
    		throw new IndexOutOfBoundsException("index is out either negative or too large");
    	}
    }


    /**
     * Reference to the head node of the linked list.
     * Normally, you would not do this, but we need it
     * for grading your work.
     *
     * You will get a 0 if you do not implement this method.
     *
     * @return Node representing the head of the linked list
     */
    public Node<T> getHead() {
    	return head;
    }

    /**
     * Reference to the tail node of the linked list.
     * Normally, you would not do this, but we need it
     * for grading your work.
     *
     * You will get a 0 if you do not implement this method.
     *
     * @return Node representing the tail of the linked list
     */
    public Node<T> getTail() {
    	return tail;
    }
}
