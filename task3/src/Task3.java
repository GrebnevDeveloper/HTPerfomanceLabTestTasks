import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Использовал библиотеку Gson 2.13.1, т.к. изначально проект структурно не создавал для Maven,то библиотека в папке lib.
 * Для запуска:
 * ---если запускаем командную из корневой папки, то вводим для сборки
 * javac -cp "lib/gson-2.13.1.jar" task3/src/Task3.java -d target/classes
 * ---для запуска (json файлы тогда должны быть в task3/src/)
 * java -cp "target/classes:lib/gson-2.13.1.jar" Task3 task3/src/values.json task3/src/tests.json task3/src/report.json
 *
 * ---если запускаем командную из папки task3/src/ то для сборки
 * javac -cp "../../lib/gson-2.13.1.jar" Task3.java
 * ---для запуска (json файлы тогда должны быть в task3/src/)
 * java -cp ".:../../lib/gson-2.13.1.jar" Task3 values.json tests.json report.json
 */
public class Task3 {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Неверное количество аргументов");
            return;
        }

        String valuesPath = args[0];
        String testsPath = args[1];
        String reportPath = args[2];

        try {
            // считываем файлы и кладём в json object
            JsonObject valuesJson = JsonParser.parseReader(new FileReader(valuesPath)).getAsJsonObject();
            JsonObject testsJson = JsonParser.parseReader(new FileReader(testsPath)).getAsJsonObject();

            // Создаем копию testsJson для отчета
            Gson gson = new Gson();
            JsonObject reportJson = gson.fromJson(gson.toJson(testsJson), JsonObject.class);

            // Создаем мапу из значений, чтобы было удобнее работать со значениями
            Map<Integer, String> valuesMap = new HashMap<>();
            JsonArray valuesArray = valuesJson.getAsJsonArray("values");
            for (JsonElement element : valuesArray) {
                JsonObject valueObj = element.getAsJsonObject();
                int id = valueObj.get("id").getAsInt();
                String value = valueObj.get("value").getAsString();
                valuesMap.put(id, value);
            }

            // Обрабатываем структуру тестов
            processTestsArray(reportJson.getAsJsonArray("tests"), valuesMap);

            // Записываем результат в report.json
            // Используем gson builder, чтобы json был отформатирован
            Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
            try (FileWriter writer = new FileWriter(reportPath)) {
                gsonBuilder.toJson(reportJson, writer);
            }

            System.out.println(reportPath + " успешно сгенерирован");

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Неизвестная ошибка: " + e.getMessage());
        }
    }

    private static void processTestsArray(JsonArray testsArray, Map<Integer, String> valuesMap) {
        for (JsonElement element : testsArray) {
            JsonObject testObj = element.getAsJsonObject();
            int id = testObj.get("id").getAsInt();

            // Заполняем значение, если есть в мапе значений
            if (valuesMap.containsKey(id)) {
                testObj.addProperty("value", valuesMap.get(id));
            }

            // Рекурсивно обрабатываем вложенные элементы
            if (testObj.has("values")) {
                JsonArray nestedArray = testObj.getAsJsonArray("values");
                processTestsArray(nestedArray, valuesMap);
            }
        }
    }
}
