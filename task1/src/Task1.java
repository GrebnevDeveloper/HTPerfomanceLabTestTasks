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

    // Использую строку вместо массива, т.к. вычислений не каких делать не надо
    // и можно работать просто как с последовательностью символов
    private static void findPath(int sizeArray, int lengthInterval) {
        StringBuilder path = new StringBuilder();
        int currentStart = 1;

        do {
            path.append(currentStart);

            // Вычисляем следующий стартовый элемент:
            // -2 т.к. первая -1 компенсирует индексацию с 1 а не 0,
            // и еще -1 потому что мы уже включили currentStart элемент
            // % sizeArray - для обеспечения кругового обхода
            // +1 - возвращаемся к индексации с 1
            currentStart = ((currentStart + lengthInterval - 2) % sizeArray) + 1;

        } while (currentStart != 1);

        System.out.println(path);
    }
}
