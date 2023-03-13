package edu.emory.cs.sort.hybrid;


import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.divide_conquer.IntroSort;

import java.lang.reflect.Array;
import java.util.*;

public class HybridSortHW<T extends Comparable<T>> implements HybridSort<T> {

    private final AbstractSort<T> engine;

    public HybridSortHW() {
        engine = new IntroSort<T>(new ShellSortKnuth<T>());
    }

    @Override
    public T[] sort(T[][] input) {
        int k = input.length;
        int[] index = new int[k];
        int size = Arrays.stream(input).mapToInt(t -> t.length).sum();
        T[] output = (T[]) Array.newInstance(input[0][0].getClass(), size);

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k, Comparator.comparing(a -> input[a][index[a]]));
        for (int i = 0; i < k; i++) {
            if (input[i].length > 0) {
                presort(input[i]);
                minHeap.offer(i);
            }
        }
        int i = 0;
        while (!minHeap.isEmpty()) {
            int minIndex = minHeap.poll();
            output[i++] = input[minIndex][index[minIndex]];
            index[minIndex]++;
            if (index[minIndex] < input[minIndex].length) {
                minHeap.offer(minIndex);
            }
        }
        return output;
    }

    public void presort(T[] subinput) {
        //flip if reverse
        if (subinput[0].compareTo(subinput[subinput.length - 1]) > 0) {
            for (int i = 0, j = subinput.length - 1; i < j; i++, j--) {
                T temp = subinput[i];
                subinput[i] = subinput[j];
                subinput[j] = temp;
            }
        }

        //if sorted ascending
        /*if () {*/

        //if mostly ascending
        engine.sort(subinput);
    }


    public static void main(String[] args) {
        HybridSort<Integer> mine = new HybridSortHW<>();  // TODO: replace with your class

        Integer[][] input = {{0, 1, 2, 3}, {7, 6, 5, 4}, {0, 3, 1, 2}, {4, 7, 6, 5}, {9, 8, 11, 10}};
        Integer[] auto = mine.sort(input);
        for (int i = 0; i < auto.length; i++) {
            System.out.print(auto[i]+ " ");
        }
    }
}
/*
*
* */