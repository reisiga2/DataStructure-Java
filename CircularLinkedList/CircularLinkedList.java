/**
 * CircularLinkedList implementation
 * @author <Mostafa Reisi Gahrooei>
 * @version 1.0
 */
public class CircularLinkedList<T> implements LinkedListInterface<T> {
   private Node<T> head;
   private Node<T> tail;
   private int listSize = 0;

    @Override
    public void addAtIndex(int index, T data) {
        Node<T> current;
        Node<T> myNode;
        current = head;
        if (index > listSize  || index < 0) {
    	    throw new java.lang.IndexOutOfBoundsException("index is too large or negative");
        }
        if (index == 0) {
            addToFront(data);
        } else if (index == listSize) {
            addToBack(data);
        } else {
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
             }
            myNode = new Node<>(data, current.getNext());
            current.setNext(myNode);
            listSize++;
        }
      }

    @Override
    public T get(int index) {

    	if (index > listSize - 1 || index < 0) {
      	  throw new java.lang.IndexOutOfBoundsException("index is too large or negative");
        }

        Node<T> current;
        current = head;
        if (index == 0) {
           return current.getData();
        } else if (index == listSize - 1) {
            return tail.getData();
        } else {
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        }
            return current.getData();
    }

    @Override
    public T removeAtIndex(int index) {
    	if (index > listSize - 1 || index < 0) {
        	  throw new java.lang.IndexOutOfBoundsException("index is too large or negative");
        }
        if (isEmpty()) {
        	return null;
        }
        T myData;
        Node<T> current;
        current = head;
        if (index == 0) {
            myData = removeFromFront();
            if (listSize == 0) {
          	  tail = null;
            }
            return myData;
        } else {
            for (int i = 0; i < index - 1; i++) {
               current = current.getNext();
            }
          Node<T> nodeToRemove = current.getNext();
          myData = nodeToRemove.getData();
          current.setNext(nodeToRemove.getNext());
          listSize--;
          if (listSize == 0) {
        	  tail = null;
          }
          return myData;
        }
    }

    @Override
    public void addToFront(T t) {
        Node<T> myNode = new Node<>(t, head);
    	if (head == null) {
            head = myNode;
            tail = myNode;
            head.setNext(myNode);
            tail.setNext(myNode);
        } else {
        	 tail.setNext(myNode);
             head = myNode;
        }
        listSize++;	
    }

    @Override
    public void addToBack(T t) {
    	Node<T> myNode = new Node<>(t, head);
    	if (head == null) {
            addToFront(t);
        } else {
            tail.setNext(myNode);
            tail = myNode;
            listSize++;
        }

    }

    @Override
    public T removeFromFront() {
        T myData;
        if (listSize == 0) {
            return null;
        } else if (listSize == 1) {
            myData = head.getData();
            head = null;
            tail = null;
            listSize--;
            return myData;
        } else {
            myData = head.getData();
            head = head.getNext();
            tail.setNext(head);
            listSize--;
            return myData;
        }
    }

    @Override
    public T removeFromBack() {
        T myData;
        if (listSize == 0) {
            return null;
        } else if (listSize == 1) {
            myData = head.getData();
            head = null;
            tail = null;
            return myData;
        } else {
            myData = tail.getData();
            Node<T> current = head;
            for (int i = 0; i < listSize - 1; i++) {
               current = current.getNext();
            }
          current.setNext(head);
          listSize--;
          return myData;
        }
    }

    @Override
    public T[] toList() {
        Object[] myList = (T[]) new Object[listSize];
        if (listSize == 0) {
        	return null;
        } else {
            Node<T> current = head;
            for (int i = 0; i <= listSize - 1; i++) {
                myList[i] = current.getData();
                current = current.getNext();
            }
        } 
     return (T[]) myList;
    }

    @Override
    public boolean isEmpty() {
        return (listSize == 0);
    }

    @Override
    public int size() {
        return listSize;
    }

    @Override
    public void clear() {
      head = null;
      tail = null;
      listSize = 0;
    }

    /**
     * Reference to the head node of the linked list.
     * Normally, you would not do this, but we need it
     * for grading your work.
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
     * @return Node representing the tail of the linked list
     */
    public Node<T> getTail() {
        return tail;
    }

    /**
     * This method is for your testing purposes.
     * You may choose to implement it if you wish.
     */
    @Override
    public String toString() {
        return "";
    }
}

