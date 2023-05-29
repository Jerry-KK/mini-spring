package com.minis.exceptions;


/**
 * @author lethe
 * @date 2023/5/29 21:40
 */
public class BeansException extends Exception{

    public BeansException() {
        super();
    }

    public BeansException(String message) {
        super(message);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeansException(Throwable cause) {
        super(cause);
    }

}
