package com.fintechcorp.validation.strategies;

import com.fintechcorp.annotations.Range;
import com.fintechcorp.validation.ValidationStrategy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class RangeStrategy implements ValidationStrategy {
    @Override
    public String validate(Field field, Object value, Annotation annotation) {
        if (value instanceof Number) {
            double numVal = ((Number) value).doubleValue();
            Range range = (Range) annotation;

            if (numVal < range.min() || numVal > range.max()) {
                return range.message();
            }
        }
        return null;
    }
}