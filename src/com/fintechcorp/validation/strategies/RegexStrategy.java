package com.fintechcorp.validation.strategies;

import com.fintechcorp.annotations.Regex;
import com.fintechcorp.validation.ValidationStrategy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class RegexStrategy implements ValidationStrategy {
    @Override
    public String validate(Field field, Object value, Annotation annotation) {
        if (value instanceof String) {
            String strVal = (String) value;
            Regex regexInfo = (Regex) annotation;
            if (!strVal.matches(regexInfo.pattern())) {
                return regexInfo.message();
            }
        }
        return null;
    }
}