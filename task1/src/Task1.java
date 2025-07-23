import java.util.InputMismatchException;
import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Передано неверное количество аргументов");
            return;
        }
        try {
            int sizeArray = Integer.parseInt(args[0]);
            int lengthInterval = Integer.parseInt(args[1]);

            findPath(sizeArray, lengthInterval);

        } catch (NumberFormatException e) {
            System.err.println("Ошибка: Введены нечисловые данные!");
        } catch (Exception e) {
            System.err.println("Неизвестная ошибка: " + e.getMessage());
        }
    }

    private static void findPath(int sizeArray, int lengthInterval) {
        StringBuilder path = new StringBuilder();
        int currentStart = 1;

        do {
            path.append(currentStart);

            currentStart = ((currentStart + lengthInterval - 2) % sizeArray) + 1;

        } while (currentStart != 1);

        System.out.println(path);
    }
}
