package com.fintechcorp.validation.strategies;

import com.fintechcorp.annotations.NotNull;
import com.fintechcorp.validation.ValidationStrategy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class NotNullStrategy implements ValidationStrategy {
    @Override
    public String validate(Field field, Object value, Annotation annotation) {
        if (value == null) {
            return ((NotNull) annotation).message();
        }
        return null;
    }
}