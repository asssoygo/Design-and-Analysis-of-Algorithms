import java.util.Random;

public class Main {

    private static final Random rand = new Random();

    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right) {
        while (left < right) {

            int pivotIndex = left + rand.nextInt(right - left + 1);
            swap(arr, pivotIndex, right);


            int pivotFinalPos = partition(arr, left, right);


            int leftSize = pivotFinalPos - left;
            int rightSize = right - pivotFinalPos;


            if (leftSize < rightSize) {
                quickSort(arr, left, pivotFinalPos - 1);
                left = pivotFinalPos + 1;  // Итеративно работаем с большей частью
            } else {
                quickSort(arr, pivotFinalPos + 1, right);
                right = pivotFinalPos - 1; // Итеративно работаем с большей частью
            }
        }
    }


    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, right);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    public static void main(String[] args) {
        int[] array = {10, 7, 8, 9, 1, 5};
        quickSort(array);
        for (int num : array) {
            System.out.print(num + " ");
        }
    }
}
