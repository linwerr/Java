import java.util.HashMap;
import java.util.Scanner;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //maxString();
        //arrayMerge();
        //maxSum();
        //rotateMatrix();
        //findPair();
        //sumOfElements();
        //maxInRows();
        rotateMatrix2();
    }


    public static void maxString() {
        System.out.println("Задание 1");
        Scanner scanner = new Scanner(System.in);
        // Ввод строки для нахождения наибольшей подстроки без повторяющихся символов
        System.out.print("Введите строку: ");
        String s = scanner.nextLine();
        int n = s.length();
        HashMap<Character, Integer> charIndexMap = new HashMap<>();
        int maxLength = 0;
        int start = 0;
        String longestSubstring = "";

        for (int end = 0; end < n; end++) {
            char currentChar = s.charAt(end);
            // Если символ уже встречался, сдвигаем начало подстроки
            if (charIndexMap.containsKey(currentChar)) {
                start = Math.max(start, charIndexMap.get(currentChar) + 1);
            }
            // Обновляем индекс текущего символа
            charIndexMap.put(currentChar, end);
            // Проверяем длину текущей подстроки
            if (end - start + 1 > maxLength) {
                maxLength = end - start + 1;
                longestSubstring = s.substring(start, end + 1);
            }
        }
        System.out.println("Наибольшая подстрока без повторяющихся символов: " + longestSubstring);
    }



    public static void arrayMerge() {
        System.out.println("Задание 2");
        Scanner scanner = new Scanner(System.in);

        // Ввод первого отсортированного массива
        System.out.print("Введите длину первого отсортированного массива: ");
        int length1 = scanner.nextInt();
        int[] array1 = new int[length1];
        System.out.println("Введите элементы первого массива:");
        for (int i = 0; i < length1; i++) {
            array1[i] = scanner.nextInt();
        }

        // Ввод второго отсортированного массива
        System.out.print("Введите длину второго отсортированного массива: ");
        int length2 = scanner.nextInt();
        int[] array2 = new int[length2];
        System.out.println("Введите элементы второго массива:");
        for (int i = 0; i < length2; i++) {
            array2[i] = scanner.nextInt();
        }

        int[] mergedArray = new int[array1.length + array2.length];
        int i = 0, j = 0, k = 0;

        // Слияние массивов
        while (i < array1.length && j < array2.length) {
            if (array1[i] < array2[j]) {
                mergedArray[k++] = array1[i++];
            } else {
                mergedArray[k++] = array2[j++];
            }
        }

        // Копируем оставшиеся элементы из первого массива, если есть
        while (i < array1.length) {
            mergedArray[k++] = array1[i++];
        }

        // Копируем оставшиеся элементы из второго массива, если есть
        while (j < array2.length) {
            mergedArray[k++] = array2[j++];
        }
        System.out.println("Объединённый массив: " + Arrays.toString(mergedArray));
    }



    public static void maxSum() {
        System.out.println("Задание 3");
        Scanner scanner = new Scanner(System.in);
        // Ввод массива целых чисел
        System.out.print("Введите длину массива: ");
        int length = scanner.nextInt();
        int[] array = new int[length];
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < length; i++) {
            array[i] = scanner.nextInt();
        }

        int maxS = Integer.MIN_VALUE; // Инициализируем минимальным значением
        int currentSum = 0;

        for (int num : array) {
            currentSum += num;

            if (currentSum > maxS) {
                maxS = currentSum;
            }

            if (currentSum < 0) {
                currentSum = 0;
            }
        }
        System.out.println("Максимальная сумма подмассива: " + maxS);
    }




    public static void rotateMatrix() {
        System.out.println("Задание 4");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество строк: ");
        int rows = scanner.nextInt();
        System.out.print("Введите количество столбцов: ");
        int cols = scanner.nextInt();
        // Инициализация и ввод элементов массива
        int[][] matrix = new int[rows][cols];
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        int[][] rotatedMatrix = new int[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotatedMatrix[j][rows - 1 - i] = matrix[i][j];
            }
        }

        System.out.println("Повернутый массив на 90 градусов по часовой стрелке:");
        for (int[] row : rotatedMatrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }




    public static void findPair() {
        System.out.println("Задание 5");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите длину массива: ");
        int length = scanner.nextInt();
        int[] array = new int[length];
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < length; i++) {
            array[i] = scanner.nextInt();
        }
        System.out.print("Введите сумму (target): ");
        int target = scanner.nextInt();

        // Вызов метода для поиска пары
        int[] result = findPairWithSum(array, target);
        if (result != null) {
            System.out.println("Найдена пара: " + result[0] + " " + result[1]);
        } else {
            System.out.println("null");
        }
    }

    // Метод для нахождения пары элементов, сумма которых равна target
    public static int[] findPairWithSum(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] + array[j] == target) {
                    return new int[] { array[i], array[j] };
                }
            }
        }
        return null; // Если пара не найдена
    }




    public static void sumOfElements() {
        System.out.println("Задание 6");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество строк: ");
        int rows = scanner.nextInt();
        System.out.print("Введите количество столбцов: ");
        int cols = scanner.nextInt();

        int[][] matrix = new int[rows][cols];
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        int sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sum += matrix[i][j];
            }
        }
        System.out.println("Сумма всех элементов в массиве: " + sum);
        scanner.close();
    }




    public static void maxInRows() {
        System.out.println("Задание 7");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество строк: ");
        int rows = scanner.nextInt();
        System.out.print("Введите количество столбцов: ");
        int cols = scanner.nextInt();

        int[][] matrix = new int[rows][cols];
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        // Поиск максимальных элементов в каждой строке
        int[] maxValues = new int[rows];
        for (int i = 0; i < matrix.length; i++) {
            int max = matrix[i][0]; // Предполагаем, что первый элемент - максимальный
            for (int j = 1; j < matrix[i].length; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
            maxValues[i] = max; // Сохраняем максимальный элемент в массив
        }

        System.out.println("Максимальные элементы в каждой строке:");
        for (int max : maxValues) {
            System.out.print(max + " ");
        }
        scanner.close();
    }




    public static void rotateMatrix2() {
        System.out.println("Задание 8");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество строк: ");
        int rows = scanner.nextInt();
        System.out.print("Введите количество столбцов: ");
        int cols = scanner.nextInt();

        int[][] matrix = new int[rows][cols];
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        // Поворот массива на 90 градусов против часовой стрелки
        int[][] rotated = new int[cols][rows]; // Новый массив с изменёнными размерами

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[cols - 1 - j][i] = matrix[i][j];
            }
        }

        System.out.println("Массив после поворота на 90 градусов против часовой стрелки:");
        for (int[] row : rotated) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
        scanner.close();
    }
}
