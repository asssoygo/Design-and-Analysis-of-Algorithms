package MergeSort;

public class MergeSortSimple {

    private static final int CUTOFF = 10;
    private static int comparisons = 0;
    private static int maxDepth = 0;

    public static void mergeSort(int[] arr) {
        comparisons = 0;
        maxDepth = 0;
        int[] buffer = new int[arr.length];
        mergeSort(arr, buffer, 0, arr.length - 1, 1);
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Max recursion depth: " + maxDepth);
    }

    private static void mergeSort(int[] arr, int[] buffer, int left, int right, int depth) {
        if (depth > maxDepth) maxDepth = depth;

        if (right - left <= CUTOFF) {
            insertionSort(arr, left, right);
            return;
        }

        int mid = left + (right - left) / 2;
        mergeSort(arr, buffer, left, mid, depth + 1);
        mergeSort(arr, buffer, mid + 1, right, depth + 1);

        if (arr[mid] <= arr[mid + 1]) return;

        merge(arr, buffer, left, mid, right);
    }

    private static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                comparisons++;
                arr[j + 1] = arr[j];
                j--;
            }
            if (j >= left) comparisons++;
            arr[j + 1] = key;
        }
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right) {

        for (int i = left; i <= right; i++) {
            buffer[i] = arr[i];
        }

        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++) {
            if (i > mid) {
                arr[k] = buffer[j++];
            } else if (j > right) {
                arr[k] = buffer[i++];
            } else {
                comparisons++;
                if (buffer[i] <= buffer[j]) {
                    arr[k] = buffer[i++];
                } else {
                    arr[k] = buffer[j++];
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 1, 5, 6, 3, 4, 8, 7, 0};
        System.out.println("Before sorting:");
        printArray(arr);

        mergeSort(arr);

        System.out.println("After sorting:");
        printArray(arr);
    }

    private static void printArray(int[] arr) {
        for (int n : arr) System.out.print(n + " ");
        System.out.println();
    }
}
