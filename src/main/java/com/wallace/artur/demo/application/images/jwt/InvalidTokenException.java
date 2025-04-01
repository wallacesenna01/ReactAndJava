package com.wallace.artur.demo.application.images.jwt;

public class InvalidTokenException extends RuntimeException{

    public  InvalidTokenException(String msg) {
        super(msg);
    }
}
