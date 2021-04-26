package ru.inovus.numbergenerator.exception;

public class RegistrationNumberNotFoundException extends RuntimeException{
    public RegistrationNumberNotFoundException(String message) {
        super(message);
    }
}
