import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HashMap<K, V> implements HashMapInterface<K, V>, Gradable<K, V> {

    // Do not make any new instance variables.
    private MapEntry<K, V>[] table;
    private int size;

    public HashMap() {
        table = (MapEntry<K, V>[]) new MapEntry[STARTING_SIZE];
    }

    @Override
    public V add(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("input was null");
        }
        if (!hasSpace()) {
            reGrow();
        }
        int index = probe(key, true);
        V myValue;
        MapEntry<K, V> e = table[index];
        if (e == null) {
            table[index] = new MapEntry<>(key, value);
            size++;
            return null;
        } else if (e.isRemoved()) {
            myValue = e.getValue();
            table[index] = new MapEntry<>(key, value);
        } else {
            myValue = e.getValue();
            table[index].setValue(value);
        }
        size++;
        return myValue;
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("input was null");
        }
        int index = probe(key, false);
        if (table[index] != null) {
            table[index].setRemoved(true);
            size--;
            return table[index].getValue();
        }
        return null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("input was null");
        }
        int index = probe(key, false);
        if (table[index] != null && !table[index].isRemoved()) {
            return table[index].getValue();
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    @Override
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[STARTING_SIZE];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public MapEntry<K, V>[] toArray() {
        return table;
    }

    @Override
    public Set<K> keySet() {
        HashSet<K> s = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                s.add(table[i].getKey());
            }
        }
        return s;
    }

    @Override
    public List<V> values() {
        ArrayList<V> myList = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                myList.add(table[i].getValue());
            }
        }
        return myList;
    }
/*
 * increases the size of the table by a factor of 2.
 * When filling the new array, one should do the hashing
 * to put the items in the right place.
 *
 */
    private void reGrow() {
        int newCapacity = 2 * table.length;
        MapEntry<K, V>[] newTable =
            (MapEntry<K, V>[]) new MapEntry[newCapacity];
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null || table[i].isRemoved()) {
                newTable[i] = null;
            } else {
                int index =
                    Math.abs(table[i].getKey().hashCode()) % newCapacity;
                newTable[index] = table[i];
            }
        }
        table = newTable;
    }
/*
 * give an index of the array that is associated with a given key
 * @param key
 * @return index of the array
 */
    private int getIndex(K key) {
        return Math.abs(key.hashCode() % table.length);
    }
/*
 * gives the index that a value should go in based on its key.
 * Checks if the first place is empty or removed or has the
 * same key then returns
 * the index. Otherwise moves to the next slot and do the same check.
 * @param key
 * @return index where the value with the given key should go.
 */
    private int probe(K key, boolean addIndicator) {
        int index = getIndex(key);
        for (int i = 0; i < table.length; i++) {
            if (table[index] == null) {
                return index;
            } else if (addIndicator && table[index].isRemoved()) {
                return index;
            } else if (key.equals(table[index].getKey())) {
                return index;
            }
            index = (index + 1) % table.length;
        }
        return -1;
    }
/*
 * checks if the table still have enough space based on its load factor.
 * @return true or false
 */
    private boolean hasSpace() {
        double ratio = (double) size / table.length;
        return (ratio < MAX_LOAD_FACTOR);
    }
}
