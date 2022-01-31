package com.programming.redditClone.exception;

public class SpringRedditCloneException extends RuntimeException {
    public SpringRedditCloneException(String exceptionMessage) {
//        we should not send to the user technical exception
//        we should send understandable/custom message
        super(exceptionMessage);
    }
}
