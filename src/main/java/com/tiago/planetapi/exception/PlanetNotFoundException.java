package com.tiago.planetapi.exception;

public class PlanetNotFoundException extends RuntimeException{
    public PlanetNotFoundException(String message){
        super(message);
    }
}
