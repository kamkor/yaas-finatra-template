package me.kamkor.wishlists.repository.document;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@BindingAnnotation
@Target(PARAMETER)
@Retention(RUNTIME)
/**
 * Identifies client for the communication with https://devportal.yaas.io/services/document/latest/index.html
 */
public @interface DocumentClient {}

