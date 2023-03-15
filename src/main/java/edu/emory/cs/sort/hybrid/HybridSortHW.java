package edu.emory.cs.sort.hybrid;


import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.divide_conquer.QuickSort;

import java.lang.reflect.Array;


public class HybridSortHW<T extends Comparable<T>> implements HybridSort<T> {

    private final AbstractSort<T> sortOrg;
    private final AbstractSort<T> sortRand;

    public HybridSortHW() {
        sortOrg = new ShellSortKnuth<>();
        sortRand = new QuickSort<>();
    }

    @Override
    public T[] sort(T[][] input) {
        int length = input.length;
        //sort arrays
        for (int i = 0; i < length; i++) {
            presort(input[i]);
        }
        //combine arrays
        while (length> 1) {
            int k = (length+ 1) / 2;
            for (int i = 0; i < length/ 2; i++) {
                input[i] = merge(input[i], input[i + k]);
            }
            length= k;
        }

        return input[0];
    }

    public void presort(T[] subinput) {
        int sortindex = 0;
        //check a sample of elements to compare to the first one to get an idea of ascending/descending
        int step = (int) Math.log(subinput.length);
        for (int i = step; i < subinput.length; i += step) {
            if (subinput[0].compareTo(subinput[i]) > 0) {
                sortindex++;
            }
        }

        //flip if the array seems to be mostly descending
        if (sortindex > 0.8 * subinput.length/step) {
            for (int i = 0, j = subinput.length - 1; i < j; i++, j--) {
                T temp = subinput[i];
                subinput[i] = subinput[j];
                subinput[j] = temp;
            }
            sortOrg.sort(subinput);
        }

        //ascending case
        else if (sortindex <= 0.2 * subinput.length/step) {
            sortOrg.sort(subinput);
        }

        //random case
        else {
            sortRand.sort(subinput);
        }
    }

    public T[] merge(T[] left, T[] right) {
        T[] merged = (T[]) Array.newInstance(left.getClass().getComponentType(), left.length + right.length);
        //compare between the two arrays
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i].compareTo(right[j]) < 0) {
                merged[k++] = left[i++];
            } else {
                merged[k++] = right[j++];
            }
        }
        //cases for when an array runs out of elements
        if (i == left.length) {
            System.arraycopy(right, j, merged, k, right.length - j);
        } else {
            System.arraycopy(left, i, merged, k, left.length - i);
        }

        return merged;
    }

}
