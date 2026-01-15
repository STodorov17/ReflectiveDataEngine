package com.fintechcorp.validation;

import com.fintechcorp.annotations.NotNull;
import com.fintechcorp.annotations.Range;
import com.fintechcorp.annotations.Regex;
import com.fintechcorp.validation.strategies.NotNullStrategy;
import com.fintechcorp.validation.strategies.RangeStrategy;
import com.fintechcorp.validation.strategies.RegexStrategy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class Validator {

    private final Map<Class<? extends Annotation>, ValidationStrategy> strategies = new HashMap<>();

    public Validator() {
        strategies.put(NotNull.class, new NotNullStrategy());
        strategies.put(Regex.class, new RegexStrategy());
        strategies.put(Range.class, new RangeStrategy());
    }
    /**
     * Validates a list of objects based on field annotations.
     * Uses the Strategy Pattern to check constraints like @NotNull, @Range, and @Regex.
     *
     * @param objects The list of objects to validate.
     * @param <T> The type of objects.
     * @return A Map where the key is the invalid object and the value is a Set of error messages.
     * Returns an empty map if all objects are valid.
     */
    public <T> Map<T, Set<String>> validate(List<T> objects) {
        Map<T, Set<String>> validationErrors = new LinkedHashMap<>();

        for (T obj : objects) {
            Set<String> errorsForObject = new HashSet<>();

            Field[] fields = obj.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);

                    for (Map.Entry<Class<? extends Annotation>, ValidationStrategy> entry : strategies.entrySet()) {
                        Class<? extends Annotation> annotationClass = entry.getKey();

                        if (field.isAnnotationPresent(annotationClass)) {
                            Annotation annotationInstance = field.getAnnotation(annotationClass);
                            ValidationStrategy strategy = entry.getValue();

                            String error = strategy.validate(field, value, annotationInstance);

                            if (error != null) {
                                errorsForObject.add("Field '" + field.getName() + "': " + error);
                            }
                        }
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (!errorsForObject.isEmpty()) {
                validationErrors.put(obj, errorsForObject);
            }
        }
        return validationErrors;
    }
}