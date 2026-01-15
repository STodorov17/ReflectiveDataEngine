package com.fintechcorp.engine;

import com.fintechcorp.annotations.Column;
import com.fintechcorp.annotations.FileSource;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GenericFileParser {
    /**
     * Parses a flat file into a list of objects of type T based on annotations.
     * <p>
     * This method reads the file line-by-line, instantiates the target class using Reflection.
     * </p>
     *
     * @param filePath The path to the source file.
     * @param clazz The target Class type.
     * @param <T> The generic type of the result objects.
     * @return A List of populated objects of type T.
     * @throws IOException If the file cannot be read.
     * @throws ReflectiveOperationException If the object cannot be instantiated.
     * @throws RuntimeException If data conversion fails.
     */
    public <T> List<T> parse(String filePath, Class<T> clazz) throws IOException, ReflectiveOperationException {
        List<T> resultList = new ArrayList<>();

        if (!clazz.isAnnotationPresent(FileSource.class)) {
            throw new IllegalArgumentException("Class " + clazz.getSimpleName() + " is missing FileSource.");
        }

        String delimiter = clazz.getAnnotation(FileSource.class).delimiter();

        String splitRegex = Pattern.quote(delimiter);

        List<String> lines = Files.readAllLines(Path.of(filePath));

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }

            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();

            String[] data = line.split(splitRegex, -1);

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    int index = column.index();

                    if (index < data.length) {
                        String rawValue = data[index].trim();

                        Object convertedValue = convert(field.getType(), rawValue);

                        field.setAccessible(true);
                        field.set(instance, convertedValue);
                    }
                }
            }
            resultList.add(instance);
        }

        return resultList;
    }

    private Object convert(Class<?> type, String value) {
        if (value == null || value.isEmpty()) {
            if (type == int.class) return 0;
            if (type == double.class) return 0.0;
            if (type == boolean.class) return false;
            return null;
        }

        try {
            if (type == int.class || type == Integer.class) {
                return Integer.parseInt(value);
            } else if (type == double.class || type == Double.class) {
                return Double.parseDouble(value);
            } else if (type == boolean.class || type == Boolean.class) {
                return Boolean.parseBoolean(value);
            } else if (type == LocalDate.class) {
                return LocalDate.parse(value);
            } else if (type == String.class) {
                return value;
            }
        } catch (Exception e) {
            throw new RuntimeException("Parse Error: Cannot convert value '" + value + "' to type " + type.getSimpleName());
        }

        return value;
    }
}