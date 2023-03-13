package edu.emory.cs.sort.distribution;


import java.util.ArrayDeque;
import java.util.Deque;

public class RadixSortQuiz extends RadixSort {
    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        // checks if the input has more than one element
        if (array == null || array.length <= 1 || beginIndex >= endIndex)
            return;

        int maxBit = getMaxBit(array, beginIndex, endIndex);

        //iterate through the bits
        for (int bit = 0; bit < maxBit; bit++) {
            int div = (int)Math.pow(10, bit);

            // Create 10 buckets to sort the keys into
            Deque<Integer>[] buckets = new Deque[10];
            for (int i = 0; i < 10; i++) {
                buckets[i] = new ArrayDeque<>();
            }

            // Add each element to the corresponding bucket
            for (int i = beginIndex; i < endIndex; i++) {
                int digit = (array[i] / div) % 10;
                buckets[digit].add(array[i]);
            }

            // Merge elements in all buckets back to the input array
            int currentIndex = beginIndex;
            for (Deque<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    array[currentIndex++] = bucket.remove();
                }
            }
        }
    }
}
