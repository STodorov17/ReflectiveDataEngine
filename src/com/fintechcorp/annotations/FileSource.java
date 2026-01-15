package com.fintechcorp.annotations; // Внимавай да съвпада с името на пакета ти!

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FileSource {
    String delimiter();
}