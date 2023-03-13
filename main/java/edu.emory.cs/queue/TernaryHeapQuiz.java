package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TernaryHeapQuiz<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;

    public TernaryHeapQuiz() {
        this(Comparator.naturalOrder());
    }

    public TernaryHeapQuiz(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
        keys.add(null);
    }

    @Override
    public int size() {
        return keys.size();
    }

    /**
     * @param i1 the index of the first key in {@link #keys}.
     * @param i2 the index of the second key in {@link #keys}.
     * @return a negative integer, zero, or a positive integer as the first key is
     * less than, equal to, or greater than the second key.
     */
    private int compare(int i1, int i2) {
        return priority.compare(keys.get(i1), keys.get(i2));
    }

    @Override
    public void add(T key) {
        keys.add(key);
        swim(size()-1);
    }

    private void swim(int k) {
        while (0 < k && priority.compare(keys.get(getParentIndex(k)), keys.get(k)) < 0) {
            Collections.swap(keys, getParentIndex(k), k);
            k = getParentIndex(k);
        }
    }

    @Override
    public T remove() {
        if (isEmpty()) return null;
        Collections.swap(keys, 0, size()-1);
        T max = keys.remove(size()-1);
        sink(0);
        return max;
    }

    private void sink(int k) {
        int i, max;

        while ((i = getLeftmostChildIndex(k)) < size()) {
            max = getMaxIndex(i);
            if (priority.compare(keys.get(k), keys.get(max)) >= 0)
                break;
            Collections.swap(keys, k, max);
            k = max;
        }
    }

    private int getParentIndex(int k) {
        return (k -1 ) / 3;
    }
    private int getLeftmostChildIndex(int k) {
        return k * 3 + 1;
    }

    private int getMaxIndex(int leftmostIndex) {
        int max = leftmostIndex;

        for (int i = leftmostIndex + 1; i < size() && i < leftmostIndex + 2;i++) {
            if (priority.compare(keys.get(max), keys.get(i)) < 0)
                max = i;
        }
        return max;
    }
}