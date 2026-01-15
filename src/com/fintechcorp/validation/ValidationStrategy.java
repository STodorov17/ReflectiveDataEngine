package com.fintechcorp.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public interface ValidationStrategy {
    String validate(Field field, Object value, Annotation annotation);
}