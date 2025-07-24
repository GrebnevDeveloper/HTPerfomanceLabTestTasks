import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Task4 {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Неверное количество аргументов");
            return;
        }

        try {
            String content = Files.readString(Paths.get(args[0]));

            List<Integer> nums = Arrays.stream(content.split("\\n"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());;

            System.out.println(minMovesToEqualElements(nums));

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Ошибка при парсинге чисел: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Неизвестная ошибка: " + e.getMessage());
        }
    }

    private static int minMovesToEqualElements(List<Integer> nums) {
        Collections.sort(nums); // сортируем список
        int median = nums.get(nums.size() / 2); // ищем медианное значение
        int moves = 0;
        for (int num : nums) {
            moves += Math.abs(num - median); // находим количество шагов от числа до медианы и суммируем их
        }
        return moves;
    }
}
