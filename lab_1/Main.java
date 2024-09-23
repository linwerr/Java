import java.util.Scanner;

public class Main {

    public static void Collatz() {
        System.out.print("Задание 1");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите натуральное число: ");
        int n = scanner.nextInt();

        // Вычисление количества шагов до достижения 1
        int k = 0;
        while (n != 1) {
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = 3 * n + 1;
            }
            k++;
        }
        System.out.println("Количество шагов: " + k);
        System.out.println();
    }


    public static void Sum() {
        System.out.print("Задание 2");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество чисел n: ");
        int n = scanner.nextInt();

        int sum = 0;
        for (int i = 0; i < n; i++) {
            System.out.print("Введите число " + (i + 1) + ": ");
            int num = scanner.nextInt();
            // Чердуем знаки в зависимости от индекса
            if (i % 2 == 0) {
                sum += num; // Индексы четные прибавляем
            } else {
                sum -= num; // нечетные вычитаем
            }
        }
        System.out.println("Сумма: " + sum);
        System.out.println();
    }



    public static void TreasureMap() {
        System.out.print("Задание 3");
        System.out.println();
        Scanner scanner = new Scanner(System.in);

        // Чтение координат клада
        int targetX = scanner.nextInt();
        int targetY = scanner.nextInt();
        scanner.nextLine();

        // Координаты начальной точки
        int currentX = 0;
        int currentY = 0;
        int instructionCount = 0; // Количество указаний
        int minStepsToTreasure = -1; // Минимальное количество шагов до клада

        // Чтение указаний карты
        while (scanner.hasNextLine()) {
            String direction = scanner.nextLine();
            if (direction.equals("стоп")) {
                if (minStepsToTreasure != -1) {
                    System.out.println(minStepsToTreasure);
                } else {
                    System.out.println(-1); // Если не достигли клада
                }
                break;
            }

            int steps = scanner.nextInt();
            scanner.nextLine();

            // Обработка направления и шагов
            switch (direction) {
                case "север":
                    currentY += steps;
                    break;
                case "юг":
                    currentY -= steps;
                    break;
                case "восток":
                    currentX += steps;
                    break;
                case "запад":
                    currentX -= steps;
                    break;
            }
            instructionCount++;

            // Проверяем, достигли ли мы клада
            if (currentX == targetX && currentY == targetY) {
                if (minStepsToTreasure == -1 || instructionCount < minStepsToTreasure) {
                    minStepsToTreasure = instructionCount; // Сохраняем минимальные шаги
                }
            }
        }
    }





    public static void TruckHeight() {
        System.out.print("Задание 4");
        System.out.println();
        Scanner scanner = new Scanner(System.in);

        int numberOfRoads = Integer.parseInt(scanner.nextLine()); // Количество дорог
        int maxHeight = 0;
        int bestRoadIndex = 0;

        // Обрабатываем каждую дорогу
        for (int i = 1; i <= numberOfRoads; i++) {
            int numberOfTunnels = Integer.parseInt(scanner.nextLine()); // Количество туннелей
            int minHeight = Integer.MAX_VALUE;

            // Считываем высоту каждого туннеля
            for (int j = 0; j < numberOfTunnels; j++) {
                int tunnelHeight = Integer.parseInt(scanner.nextLine());
                if (tunnelHeight < minHeight) {
                    minHeight = tunnelHeight;
                }
            }

            // Сравниваем с максимальной высотой
            if (minHeight > maxHeight) {
                maxHeight = minHeight;
                bestRoadIndex = i;
            }
        }

        System.out.println(bestRoadIndex + " " + maxHeight);
        scanner.close();
        System.out.println();
    }



    public static void DoubleEvenChecker() {
        System.out.print("Задание 5");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите положительное трехзначное число: ");
        int number = scanner.nextInt();

        int sum = 0;
        int multiplication = 1;

        while (number > 0) {
            int digit = number % 10;
            sum += digit;
            multiplication *= digit;
            number /= 10;
        }

        if ((sum % 2 == 0) && (multiplication % 2 == 0)) {
                System.out.println("Является дважды четным");
            }
        else {
                System.out.println("Не является дважды четным");
            }
        scanner.close();
    }



    public static void main(String[] args) {
        //Collatz();
        //Sum();
        //TreasureMap();
        //TruckHeight();
        DoubleEvenChecker();
    }
}
