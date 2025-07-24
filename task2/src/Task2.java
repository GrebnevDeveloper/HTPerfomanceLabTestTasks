import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Task2 {
    // Работать будем с BigDecimal, т.к. входные числа слишком большие
    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL128;
    // Погрешность для сравнения вещественных чисел
    private static final BigDecimal ERROR_RATE = new BigDecimal("1E-10");

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Передано неверное количество аргументов");
            return;
        }

        try {
            Circle circle = readCircleFromFile(args[0]);

            readPointsAndCalculatePositions(args[1], circle);

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файлов: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Ошибка при парсинге чисел: " + e.getMessage());
        }
    }

    private static Circle readCircleFromFile(String filePath) throws IOException {
        String content = Files.readString(Paths.get(filePath));
        String[] lines = content.trim().split("\\n");
        String[] centerCoordinates = lines[0].split(" ");

        BigDecimal centerX = new BigDecimal(centerCoordinates[0]);
        BigDecimal centerY = new BigDecimal(centerCoordinates[1]);
        BigDecimal radius = new BigDecimal(lines[1]);

        return new Circle(centerX, centerY, radius);
    }

    private static void readPointsAndCalculatePositions(String filePath, Circle circle) throws IOException {
        String content = Files.readString(Paths.get(filePath));
        String[] lines = content.split("\\n");

        for (String line : lines) {

            String[] coordinates = line.split(" ");
            BigDecimal x = new BigDecimal(coordinates[0]);
            BigDecimal y = new BigDecimal(coordinates[1]);

            int position = calculatePointPosition(circle, x, y);
            System.out.println(position);
        }
    }

    // Находим расстояние от центра окружности до точки по теореме Пифагора
    private static int calculatePointPosition(Circle circle, BigDecimal x, BigDecimal y) {
        // Вычисляем разности координат
        BigDecimal deltaX = x.subtract(circle.centerX, MATH_CONTEXT);
        BigDecimal deltaY = y.subtract(circle.centerY, MATH_CONTEXT);

        // Вычисляем квадраты разностей
        BigDecimal deltaXSquare = deltaX.pow(2, MATH_CONTEXT);
        BigDecimal deltaYSquare = deltaY.pow(2, MATH_CONTEXT);

        // Вычисляем сумму квадратов, она же дистанция в квадрате
        BigDecimal distanceSquared = deltaXSquare.add(deltaYSquare, MATH_CONTEXT);

        // Вычисляем квадрат радиуса
        BigDecimal radiusSquared = circle.radius.pow(2, MATH_CONTEXT);

        // Сравниваем distanceSquared с radiusSquared с учетом погрешности
        BigDecimal difference = distanceSquared.subtract(radiusSquared).abs();

        // Для определения, что на окружности необходимо учесть погрешность вещественных чисел
        if (difference.compareTo(ERROR_RATE) < 0) {
            return 0; // на окружности
        } else if (distanceSquared.compareTo(radiusSquared) < 0) { // тут можно просто сравнить
            return 1; // внутри окружности
        } else {
            return 2; // снаружи окружности
        }
    }

    private static class Circle {
        BigDecimal centerX;
        BigDecimal centerY;
        BigDecimal radius;

        Circle(BigDecimal centerX, BigDecimal centerY, BigDecimal radius) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = radius;
        }
    }
}
