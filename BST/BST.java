/**
 * @author Mostafa Reisi 
 * @version 1.0
 */


import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class BST<T extends Comparable<T>> implements BSTInterface<T> {
	private Node<T> root;
	protected int size;

    @Override
    public void add(T data) {
        root = addHelper(root, data);
        size++;
    }
    private Node<T> addHelper(Node<T> n, T data) {
    	if (n == null) {
    		return new Node<>(data);
    	} else {
    		if (data.compareTo(n.getData()) == 0) {
    			return n;
    		} else if (data.compareTo(n.getData()) > 0) {
    			n.setRight(addHelper(n.getRight(), data));
    		} else if (data.compareTo(n.getData()) < 0) {
    			n.setLeft(addHelper(n.getLeft(), data));
    	    }
    	return n;	
    	}	
    }

    @Override
    public T remove(T data) {
    	if (contains(data)) {
    		remove(root, data);
    		size--;
    		return data;
    	}
    	return null;  
    }

    private void remove(Node<T> n, T data) {
    	if (n == null) {
    		return;
    	} else if (data.compareTo(n.getData()) > 0) {
    		remove(n.getRight(), data);
    	} else if (data.compareTo(n.getData()) < 0) {
    		remove(n.getLeft(), data);
    	} else if (n.getRight() != null && n.getLeft() != null) {
    		Node<T> toBeRemoved = smallestLarge(n.getLeft());
    		n.setData(toBeRemoved.getData());
    		remove(n.getLeft(), toBeRemoved.getData());
    	} else if (n.getRight() != null) {
    		Node<T> toRemove = n;
    		n = n.getRight();
    		toRemove = null;
    	} else if (n.getLeft() != null) {
    		Node<T> toRemove = n;
    		n = n.getLeft();
    		toRemove = null;
    	} else
    		n = null;
    }

/**
 * finds the node with smallest data that is larger than 
 * the data in the given node
 * @param Node n
 * @return Node with smallest larger number than the node n
 * 
 */
    private Node<T> smallestLarge(Node<T> n) {
    	while (n.getRight() != null) {
    		n = n.getRight();
    	}
    	return n;
    }
    @Override
    public T get(T data) {
        if (contains(data)) {
        	return data;
        } else {
        	return null;
        }
    }

    @Override
    public boolean contains(T data) {
        Node<T> temp = root;
    	while (temp != null) {
    		if (data.equals(temp.getData())) {
    			return true;
    		} else if (data.compareTo(temp.getData()) > 0) {
    			temp = temp.getRight();
    		} else if (data.compareTo(temp.getData()) < 0) {
    			temp = temp.getLeft();
    		}
    	}
        return false;
    }

   

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
    	ArrayList<T> myList = new ArrayList<T>();
        return preOrderVisit(root, myList);
    }

    private List<T> preOrderVisit(Node<T> n, ArrayList<T> myList) {
    	if (n != null) {
    		myList.add(n.getData());
    		preOrderVisit(n.getLeft(), myList);
    		preOrderVisit(n.getRight(), myList);
    	}
    	return myList;
    }

    @Override
    public List<T> postorder() {
        ArrayList<T> myList = new ArrayList<T>();
    	return postOrderVisit(root, myList);
    }

    private List<T> postOrderVisit(Node<T> n, ArrayList<T> myList) {
    	if (n != null) {
    		postOrderVisit(n.getLeft(), myList);          		
    	  	postOrderVisit(n.getRight(), myList);
    	  	myList.add(n.getData());
    	}  	
    	return myList;
    }

    @Override
    public List<T> inorder() {
        ArrayList<T> myList = new ArrayList<T>();
    	return inOrderVisit(root, myList);
    }

    private List<T> inOrderVisit(Node<T> n, ArrayList<T> myList) {
    	if (n != null) {
    		inOrderVisit(n.getLeft(), myList);
    	  	myList.add(n.getData());
    		inOrderVisit(n.getRight(), myList);
    	}
    	return myList;
    }

    @Override
    public List<T> levelorder() {
        Queue<Node<T>> myQ = new LinkedList<Node<T>>();
        ArrayList<T> myList = new ArrayList<>();
        myQ.add(root);
        while (!(myQ.isEmpty())) {
            Node<T> temp = myQ.remove();
        	myList.add(temp.getData());
        	if (temp.getLeft() != null) {
        	    myQ.add(temp.getLeft());
        	}
        	if (temp.getRight() != null) {
        	    myQ.add(temp.getRight());
        	}        	
        }
        return myList;
    }

    @Override
    public void clear() {
    	root = null;
    	size = 0;    			
    }

    @Override
    public int height() {
        if (root == null) {
		    return -1;
        } else {
    	return hight(root);
        }
    }
    
    private int hight(Node<T> n) {
    	if (n == null) {
    		return -1;
    	} else {
    		return Math.max(hight(n.getLeft()) + 1, hight(n.getRight()) + 1); 
    	}
    }

    @Override
    public Node<T> getRoot() {
        return root;
    }
}