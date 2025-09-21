package Test;

import MergeSort.MergeSortSimple;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

public class MergeSortTest {

    @Test
    public void testRandomArraySort() {
        int[] input = new Random().ints(10000, 0, 100000).toArray();
        int[] copy = Arrays.copyOf(input, input.length);
        Arrays.sort(copy);

        MergeSortSimple.mergeSort(input);
        assertArrayEquals(copy, input);
    }
}

