import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;


public class SkipList<T extends Comparable<? super T>>
    implements SkipListInterface<T> {
    private CoinFlipper coinFlipper;
    private int size;
    private Node<T> head;


    /**
     * constructs a SkipList object that stores data in ascending order
     * when an item is inserted, the flipper is called until it returns a tails
     * if for an item the flipper returns n heads, the corresponding node has
     * n + 1 levels
     *
     * @param coinFlipper the source of randomness
     */
    public SkipList(CoinFlipper coinFlipper) {
        this.coinFlipper = coinFlipper;
        Node<T> p1 = new Node<>(null, 0);
        Node<T> p2 = new Node<>(null, 0);
        p1.setNext(p2);
        head = p1;
    }

    @Override
    public T first() {
        Node<T> temp = head;
        while (temp.getNext().getData() == null) {
            temp = temp.getDown();
            if (temp == null) {
                return null;
            }
        }
        return temp.getNext().getData();
    }

    @Override
    public T last() {
        Node<T> temp = head;
        int tempLevel = temp.getLevel();
        while (tempLevel >= 0) {
            while (temp.getNext().getData() != null) {
                temp = temp.getNext();
            }
            if (temp.getDown() != null) {
                temp = temp.getDown();
            }
            tempLevel--;
        }
        return temp.getData();
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The input was null");
        }
        Node<T> temp = head;
        int tempLevel = head.getLevel();
        while (tempLevel >= 0) {
            while (temp.getNext().getData() != null
                   && data.compareTo(temp.getNext().getData()) >= 0) {
                temp = temp.getNext();
                if (data.equals(temp.getData())) {
                    return true;
                }
            }
            if (temp.getDown() != null) {
                temp = temp.getDown();
            }
            tempLevel--;
        }
        return false;
    }

    @Override
    public void put(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The input was null");
        }
        int h = giveHeight();
        if (h >= head.getLevel()) {
            for (int i = 0; i <= h - head.getLevel(); i++) {
                addLevel();
            }
        }
        Node<T> temp = head;
        while (temp.getLevel() > h) {
            if (temp.getNext().getData() == null
                || data.compareTo(temp.getNext().getData()) < 0) {
                temp = temp.getDown();
            } else {
                while (temp.getNext().getData() != null
                        && data.compareTo(temp.getNext().getData()) > 0) {
                    temp = temp.getNext();
                }
            }
        }
        List<Node<T>> path = search(data);
        int pathSize = path.size();
        ArrayList<Node<T>> toAdd = new ArrayList<>();
        for (int index = pathSize - 1; index >= h; index--) {
            Node<T> current = path.get(index);
            Node<T> newNode = new Node<>(data, current.getLevel());
            newNode.setNext(current.getNext());
            current.setNext(newNode);
            toAdd.add(newNode);
        }
        for (int i = 0; i < toAdd.size() - 1; i++) {
            toAdd.get(i).setUp(toAdd.get(i + 1));
            toAdd.get(i + 1).setDown(toAdd.get(i));
        }
        size++;
    }
/*
 * gives the path that is traversed to find the node which we want to
 * connect to the new node.
 * @param a value that goes in the new node
 * @return List of nodes
 */
    private List<Node<T>> search(T data) {
        ArrayList<Node<T>> path = new ArrayList<>();
        Node<T> temp = head;
        int tempLevel = head.getLevel();
        while (tempLevel >= 0) {
            while (temp.getNext().getData() != null
                    && data.compareTo(temp.getNext().getData()) >= 0) {
                temp = temp.getNext();
                if (data.equals(temp.getData())) {
                    throw new IllegalArgumentException("The input exists");
                }
            }
            path.add(temp);
            if (temp.getDown() != null) {
                temp = temp.getDown();
            }
            tempLevel--;
        }
        return path;
    }
/*
 * add a new level to the skip list
 */
    private void addLevel() {
        int myLevel = head.getLevel();
        myLevel++;
        Node<T> p1 = new Node<>(null, myLevel);
        Node<T> p2 = new Node<>(null, myLevel);
        p1.setNext(p2);
        p1.setDown(head);
        p2.setDown(head.getNext());
        head.setUp(p1);
        head.getNext().setUp(p2);
        head = p1;
    }
/*
 * gives the number of levels that a newly added element should be promoted.
 * @return the number of levels.
 */
    private int giveHeight() {
        int h = 0;
        while (coinFlipper.flipCoin() == CoinFlipper.Coin.HEADS) {
            h++;
        }
        return h;
    }

    @Override
    public T get(T data) {
        if (contains(data)) {
            return data;
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        head.setDown(null);
        head.setLevel(0);
        head.getNext().setDown(null);
        head.getNext().setData(null);
        head.getNext().setNext(null);
        head.getNext().setLevel(0);
    }

    @Override
    public Set<T> dataSet() {
        HashSet<T> dataSet = new HashSet<>();
        int tempLevel = head.getLevel();
        Node<T> temp = head;
        while (tempLevel > 0) {
            temp = temp.getDown();
            tempLevel--;
        }
        while (temp.getNext().getData() != null) {
            dataSet.add(temp.getNext().getData());
            temp = temp.getNext();
        }
        return dataSet;
    }

    @Override
    public T remove(T data) {
        if (contains(data)) {
            Node<T> temp = findPrevious(data);
            int myLevel = temp.getLevel();
            for (int i = myLevel; i >= 0; i--) {
                Node<T> toRemove = temp.getNext();
                temp.setNext(toRemove.getNext());
                temp = findPrevious(data);
            }
            size--;
            return data;
        }
        return null;
    }
/*
 * finds the previous nod of the node that contains a particular data
 * @param data which we are looking for
 * @return previous node of the node that contains the data
 */
    private Node<T> findPrevious(T data) {
        Node<T> temp = head;
        int tempLevel = head.getLevel();
        while (tempLevel >= 0) {
            if (temp.getNext().getData() == null
                 || data.compareTo(temp.getNext().getData()) < 0) {
                temp = temp.getDown();
                tempLevel--;
            } else if (data.equals(temp.getNext().getData())) {
                return temp;
            } else {
                temp = temp.getNext();
            }
        }
        return temp;
    }

}
