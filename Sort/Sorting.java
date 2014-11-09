import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
  * Sorting implementation
  * CS 1332 : Fall 2014
  * @author Mostafa Reisi Gahrooei
  * @version 1.0
  */
public class Sorting implements SortingInterface {

    // Do not add any instance variables.

    @Override
    public <T extends Comparable<? super T>> void bubblesort(T[] arr) {
        int n = arr.length;
        for (int i = n; i > 0; i--) {
            for (int j = 0; j < i - 1; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }
/*
 * swap two elements a, b
 * @ param two elements to be swapped
 */
    private <T extends Comparable<? super T>> void swap(T[] arr, int i, int j) {
        T temp;
        temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    @Override
    public <T extends Comparable<? super T>> void insertionsort(T[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            T temp = arr[i];
            int j = i;
            while (j > 0 && temp.compareTo(arr[j - 1]) < 0) {
                arr[j] = arr[j - 1];
                j = j - 1;
            }
            arr[j] = temp;
        }
    }

    @Override
    public <T extends Comparable<? super T>> void selectionsort(T[] arr) {
        int n = arr.length;
        int minIndex;
        for (int i = 0; i < n; i++) {
            T min = arr[i];
            minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j].compareTo(min) < 0) {
                    min = arr[j];
                    minIndex = j;
                }
            }
            swap(arr, minIndex, i);
        }
    }

    @Override
    public <T extends Comparable<? super T>> void quicksort(T[] arr, Random r) {
        quickSort(arr, 0, arr.length - 1, r);
    }

    private <T extends Comparable<? super T>>
    void quickSort(T[] arr, int left, int right, Random random) {
        if (left >= right) {
            return;
        }
        int l = left;
        int r = right;
        int rand = random.nextInt(r - l) + l;
        T pivot = arr[rand];
        while (l <= r) {
            while (arr[l].compareTo(pivot) < 0) {
                l++;
            }
            while (arr[r].compareTo(pivot) > 0) {
                r--;
            }
            if (l <= r) {
                swap(arr, l, r);
                l++;
                r--;
            }
        }
        if (r > left) {
            quickSort(arr, left, r, random);
        }
        if (l < right) {
            quickSort(arr, l, right, random);
        }
    }

    @Override
    public <T extends Comparable<? super T>> void mergesort(T[] arr) {
        int n = arr.length;
        int n1 = n / 2;
        if (n1 > 0) {
            T[] arr1 = (T[]) new Comparable[n1];
            T[] arr2 = (T[]) new Comparable[n - n1];
            for (int i = 0; i < n1; i++) {
                arr1[i] = arr[i];
            }
            for (int i = 0; i < n - n1; i++) {
                arr2[i] = arr[i + n1];
            }
            mergesort(arr1);
            mergesort(arr2);
            merge(arr, arr1, arr2);
        }
    }
/*
 * merges two arrays by sorting them
 * @param two arrays to merge and an array to that holds both
 */
    private <T extends Comparable<? super T>>
    void merge(T[] arr, T[] arr1, T[] arr2) {
        int n1 = arr1.length;
        int n2 = arr2.length;
        int i = 0;
        int j = 0;
        while (i < n1 && j < n2) {
            if (j < n2 && arr1[i].compareTo(arr2[j]) < 0) {
                arr[i + j] = arr1[i];
                i++;
            }
            if (i < n1 && arr1[i].compareTo(arr2[j]) >= 0) {
                arr[i + j] = arr2[j];
                j++;
            }
        }
        while (i == n1 && j < n2) {
            arr[i + j] = arr2[j];
            j++;
        }
        while (j == n2 && i < n1) {
            arr[i + j] = arr1[i];
            i++;
        }
    }

    @Override
    public int[] radixsort(int[] arr) {
        ArrayList<Integer> negative = new ArrayList<Integer>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0) {
                negative.add(arr[i]);
                arr[i] = 0;
            }
        }
        arr = radixsortPositive(arr);
        int numNeg = negative.size();
        if (numNeg > 0) {
            int[] negativeArr = new int[numNeg];
            for (int j = 0; j < numNeg; j++) {
                negativeArr[j] = -1 * negative.get(j);
            }
            negativeArr = radixsortPositive(negativeArr);
            for (int k = numNeg - 1; k >= 0; k--) {
                arr[k] = -1 * negativeArr[numNeg - k - 1];
            }
        }
        return arr;
    }
/*
 * sorts an array of positive integers
 * @param an array of positve integers
 * @return sorted array
 */
    private int[] radixsortPositive(int[] arr) {
        int cap = arr.length;
        int exp = 1;
        int maxDigit = findMaxDigits(arr);
        int key;
        LinkedList[] bucket = {
            new LinkedList<Integer>(),
            new LinkedList<Integer>(),
            new LinkedList<Integer>(),
            new LinkedList<Integer>(),
            new LinkedList<Integer>(),
            new LinkedList<Integer>(),
            new LinkedList<Integer>(),
            new LinkedList<Integer>(),
            new LinkedList<Integer>(),
            new LinkedList<Integer>(),
            new LinkedList<Integer>(),
        };
        for (int j = 0; j < maxDigit; j++) {
            for (int i = 0; i < cap; i++) {
                key = (arr[i] / exp) % 10;
                bucket[key].addLast(arr[i]);
            }
            arr = toArray(cap, bucket);
            exp *= 10;
        }
        return arr;
    }
/*
 * put the linkedLists in an array
 * @param Linkedlist of LinkedList
 * @return an array
 */
    private int[] toArray(int arrSize, LinkedList[] bucket) {
        int k = 0;
        int[] arr = new int[arrSize];
        for (int i = 0; i < bucket.length; i++) {
            while (!bucket[i].isEmpty()) {
                arr[k] = (Integer) bucket[i].removeFirst();
                k++;
            }
        }
        return arr;
    }
/*
 * Finds the maximum number of digits in an array
 * @param array
 * @return maximum digits
 */
    private int findMaxDigits(int[] arr) {
        int max = findMaxValue(arr);
        int k = max;
        int digit = 0;
        while (k > 0) {
            k = k / 10;
            digit++;
        }
        return digit;
    }
/*
 * find the maximum value in an array
 * @param an aray of ints
 * @return the max value in the array
 */
    private int findMaxValue(int[] arr) {
        int n = arr.length;
        int max = arr[0];
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
}
