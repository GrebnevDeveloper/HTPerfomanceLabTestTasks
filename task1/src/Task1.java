import java.util.InputMismatchException;
import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите размер массива и интервал длины через пробел: ");
            int sizeArray = scanner.nextInt();
            int lengthInterval = scanner.nextInt();

            findPath(sizeArray, lengthInterval);

        } catch (InputMismatchException e) {
            System.err.println("Ошибка: Введены нечисловые данные!");
        } catch (Exception e) {
            System.err.println("Неизвестная ошибка: " + e.getMessage());
        }
    }

    private static void findPath(int sizeArray, int lengthInterval) {
        StringBuilder path = new StringBuilder();
        int currentStart = 1;
        int step = 1;

        System.out.println("\nИнтервалы:");

        do {
            StringBuilder interval = new StringBuilder();
            System.out.print("Интервал " + step + ": ");

            for (int i = 0; i < lengthInterval; i++) {
                int position = ((currentStart + i - 1) % sizeArray) + 1;
                interval.append(position);
            }

            System.out.println(interval);

            path.append(currentStart);

            currentStart = ((currentStart + lengthInterval - 2) % sizeArray) + 1;
            step++;

        } while (currentStart != 1);

        System.out.println("Полученный путь: " + path);
    }
}
