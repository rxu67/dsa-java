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
        return keys.size() - 1;
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
        swim(size());
    }

    private void swim(int k) {
        for (; 1 < k && compare((k+1) / 3, k) < 0; k = (k+1)/3)
            Collections.swap(keys, (k+1) / 3, k);
    }

    @Override
    public T remove() {
        if (isEmpty()) return null;
        Collections.swap(keys, 1, size());
        T max = keys.remove(size());
        sink();
        return max;
    }

    private void sink() {
        for (int k = 1, i  = 2; k <= (size()-1)/3; k = i, i = 3*k - 1) { //what happens if the size  is 2?
            if (compare(i, i + 1) > 0) {
                i++;
                if (compare(i, i + 1) > 0) {
                    i++;
                }
            }
            else if (compare(i, i + 2) > 0) {
                i += 2;
            }
            if (compare(k, i) >= 0) break;
            Collections.swap(keys, k, i);
        }

    }

}