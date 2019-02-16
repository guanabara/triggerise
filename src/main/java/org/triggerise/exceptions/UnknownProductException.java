package org.triggerise.exceptions;

public class UnknownProductException extends Exception {

    public UnknownProductException(String unknownCode) {
        super(unknownCode);
    }
}
