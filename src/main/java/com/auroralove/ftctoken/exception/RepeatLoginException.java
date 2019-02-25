package com.auroralove.ftctoken.exception;

/**
 * @author zyu
 * @date 2019/2/24
 */
public class RepeatLoginException extends RuntimeException{

    public RepeatLoginException(String msg){
           super(msg);
    }
}
