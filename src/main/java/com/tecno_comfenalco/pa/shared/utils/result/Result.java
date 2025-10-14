package com.tecno_comfenalco.pa.shared.utils.result;

public class Result<T, E extends Exception> {
    private final T value;
    private final E error;

    private Result(T value, E error) {
        this.value = value;
        this.error = error;
    }

    public static <T, E extends Exception> Result<T, E> ok(T value) {
        return new Result<>(value, null);
    }

    public static <T, E extends Exception> Result<T, E> error(E error) {
        return new Result<>(null, error);
    }

    public boolean isOk() {
        return error == null;
    }

    public T getValue() {
        return value;
    }

    public E getError() {
        return error;
    }
}