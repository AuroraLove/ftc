package com.auroralove.ftctoken.exception;

/**
 * @author zyu
 * @date 2019/2/24
 */
public class MissingTokenException extends RuntimeException {

    public MissingTokenException(String s) {
        super(s);
    }
}
