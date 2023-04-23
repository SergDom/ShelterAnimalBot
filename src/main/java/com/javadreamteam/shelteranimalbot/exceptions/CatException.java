package com.javadreamteam.shelteranimalbot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CatException extends RuntimeException {
    public CatException(){
        super("Can't find a cat");
    }
}
