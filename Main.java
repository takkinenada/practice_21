package practice_21;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Enter array size: ");
        int size = scanner.nextInt();

        System.out.print("Enter minimum value: ");
        int min = scanner.nextInt();

        System.out.print("Enter maximum value: ");
        int max = scanner.nextInt();

        System.out.println("Select sorting method:");
        System.out.println("1 - Ascending");
        System.out.println("2 - Descending");
        System.out.print("Your choice: ");
        int choice = scanner.nextInt();
        boolean ascending = (choice == 1);

        int[] originalArray = new int[size];
        for (int i = 0; i < size; i++) {
            originalArray[i] = random.nextInt((max - min) + 1) + min;
        }

        int[] arrayForMerge = Arrays.copyOf(originalArray, originalArray.length);
        int[] arrayForCounting = Arrays.copyOf(originalArray, originalArray.length);

        System.out.println("\nOriginal array before sorting:");
        printArray(originalArray);

        LocalTime startMerge = LocalTime.now();
        mergeSort(arrayForMerge, 0, arrayForMerge.length - 1, ascending);
        LocalTime endMerge = LocalTime.now();

        LocalTime startCounting = LocalTime.now();
        countingSort(arrayForCounting, ascending);
        LocalTime endCounting = LocalTime.now();

        System.out.println("\nArray after Merge Sort (Злиттям):");
        printArray(arrayForMerge);

        System.out.println("\nArray after Counting Sort (Підрахунками):");
        printArray(arrayForCounting);

        Duration durationMerge = Duration.between(startMerge, endMerge);
        Duration durationCounting = Duration.between(startCounting, endCounting);

        System.out.printf("\nСортування злиттям: %d елементів за %d мс (%d нс).\n",
                size, durationMerge.toMillis(), durationMerge.toNanos());

        System.out.printf("Сортування підрахунками: %d елементів за %d мс (%d нс).\n",
                size, durationCounting.toMillis(), durationCounting.toNanos());

        scanner.close();
    }

    public static void mergeSort(int[] array, int left, int right, boolean ascending) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(array, left, mid, ascending);
            mergeSort(array, mid + 1, right, ascending);
            merge(array, left, mid, right, ascending);
        }
    }

    private static void merge(int[] array, int left, int mid, int right, boolean ascending) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(array, left, L, 0, n1);
        for (int j = 0; j < n2; ++j) {
            R[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            boolean condition = ascending ? (L[i] <= R[j]) : (L[i] >= R[j]);
            if (condition) {
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

    public static void countingSort(int[] array, boolean ascending) {
        if (array.length == 0) return;

        int max = array[0];
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) max = array[i];
            if (array[i] < min) min = array[i];
        }

        int range = max - min + 1;
        int[] count = new int[range];
        int[] output = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            count[array[i] - min]++;
        }

        if (ascending) {
            for (int i = 1; i < count.length; i++) {
                count[i] += count[i - 1];
            }
        } else {
            for (int i = count.length - 2; i >= 0; i--) {
                count[i] += count[i + 1];
            }
        }

        for (int i = array.length - 1; i >= 0; i--) {
            output[count[array[i] - min] - 1] = array[i];
            count[array[i] - min]--;
        }

        System.arraycopy(output, 0, array, 0, array.length);
    }

    private static void printArray(int[] array) {
        if (array.length <= 50) {
            System.out.println(Arrays.toString(array));
        } else {
            System.out.print("[");
            for (int i = 0; i < 15; i++) {
                System.out.print(array[i] + ", ");
            }
            System.out.print("... ");
            for (int i = array.length - 15; i < array.length; i++) {
                System.out.print(array[i] + (i == array.length - 1 ? "" : ", "));
            }
            System.out.println("]");
        }
    }
}