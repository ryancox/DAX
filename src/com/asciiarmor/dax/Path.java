package com.asciiarmor.dax;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

/**
 *
 * Annotation to store xpath associated with method
 */
@Retention(RetentionPolicy.RUNTIME) 
public @interface Path {
	String value();
}