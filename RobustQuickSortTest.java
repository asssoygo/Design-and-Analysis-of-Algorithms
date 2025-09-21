public class RobustQuickSortTest {

    // Helper method to check if array is sorted ascending
    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i-1]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        // Test case 1: random array
        int[] test1 = {10, 7, 8, 9, 1, 5};
        Main.quickSort(test1);
        assert isSorted(test1) : "Test 1 failed";

        // Test case 2: already sorted array
        int[] test2 = {1, 2, 3, 4, 5, 6};
        Main.quickSort(test2);
        assert isSorted(test2) : "Test 2 failed";

        // Test case 3: reversed array
        int[] test3 = {6, 5, 4, 3, 2, 1};
        Main.quickSort(test3);
        assert isSorted(test3) : "Test 3 failed";

        // Test case 4: array with duplicates
        int[] test4 = {4, 2, 4, 3, 2, 1};
        Main.quickSort(test4);
        assert isSorted(test4) : "Test 4 failed";

        // Test case 5: array with one element
        int[] test5 = {42};
        Main.quickSort(test5);
        assert isSorted(test5) : "Test 5 failed";

        // Test case 6: empty array
        int[] test6 = {};
        Main.quickSort(test6);
        assert isSorted(test6) : "Test 6 failed";

        System.out.println("All tests passed!");
    }
}
