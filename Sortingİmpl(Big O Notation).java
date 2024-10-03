public class hw3 {

    public static void main(String[] args) {
        
    }
}

 class Algorithms {

    // Access Element
    public static int accessElement(int[] array, int index) {
        // O(1)
        return array[index];
    }

    // Find Maximum
    public static int findMax(int[] array) {
        // O(n)
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    // Bubble Sort
    public static void bubbleSort(int[] array) {
        // O(n^2)
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    // Swap
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    // Merge Sort
    public static void mergeSort(int[] array, int left, int right) {
        // O(n log n)
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    private static void merge(int[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(array, left, L, 0, n1);
        System.arraycopy(array, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            array[k] = R[j];
            j++;
            k++;
        }
    }

    // Binary Search
    public static int binarySearch(int[] array, int target) {
        // O(log n)
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (array[mid] == target) {
                return mid;
            } else if (array[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1; // Target not found
    }

    // Testing methods
    public static void main(String[] args) {
        int[] array = {5, 3, 8, 6, 2, 7, 4, 1};
        int index = 3;
        System.out.println("Access Element at index " + index + ": " + accessElement(array, index));

        System.out.println("Max element: " + findMax(array));

        System.out.println("Array before Bubble Sort: ");
        printArray(array);
        bubbleSort(array);
        System.out.println("Array after Bubble Sort: ");
        printArray(array);

        int[] mergeSortArray = {5, 3, 8, 6, 2, 7, 4, 1};
        System.out.println("Array before Merge Sort: ");
        printArray(mergeSortArray);
        mergeSort(mergeSortArray, 0, mergeSortArray.length - 1);
        System.out.println("Array after Merge Sort: ");
        printArray(mergeSortArray);

        int target = 6;
        int indexFound = binarySearch(mergeSortArray, target);
        if (indexFound != -1) {
            System.out.println("Element " + target + " found at index: " + indexFound);
        } else {
            System.out.println("Element " + target + " not found.");
        }
    }

    private static void printArray(int[] array) {
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}