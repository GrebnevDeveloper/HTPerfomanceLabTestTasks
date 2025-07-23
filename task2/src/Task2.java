import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Task2 {
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
        double centerX = Double.parseDouble(centerCoordinates[0]);
        double centerY = Double.parseDouble(centerCoordinates[1]);

        double radius = Double.parseDouble(lines[1]);

        return new Circle(centerX, centerY, radius);
    }

    private static void readPointsAndCalculatePositions(String filePath, Circle circle) throws IOException {
        String content = Files.readString(Paths.get(filePath));
        String[] lines = content.split("\\n");

        for (String line : lines) {

            String[] coordinates = line.split(" ");
            double x = Double.parseDouble(coordinates[0]);
            double y = Double.parseDouble(coordinates[1]);

            int position = calculatePointPosition(circle, x, y);
            System.out.println(position);
        }
    }

    private static int calculatePointPosition(Circle circle, double x, double y) {
        // Находим расстояние от центра окружности до точки по теореме Пифагора
        double distance = Math.sqrt(Math.pow(x - circle.centerX, 2) + Math.pow(y - circle.centerY, 2));

        // Так как работаем в вещественными числами, то нужно учитывать погрешность
        double errorRate = 1e-10;

        if (Math.abs(distance - circle.radius) < errorRate) {
            return 0; // на окружности
        } else if (distance < circle.radius) {
            return 1; // в окружности
        } else {
            return 2; // снаружи окружности
        }
    }

    private static class Circle {
        double centerX;
        double centerY;
        double radius;

        Circle(double centerX, double centerY, double radius) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = radius;
        }
    }
}
