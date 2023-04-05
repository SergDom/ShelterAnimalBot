package com.javadreamteam.shelteranimalbot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DogException extends RuntimeException {
    public DogException (){
        super("Can't find a dog");
    }

}
