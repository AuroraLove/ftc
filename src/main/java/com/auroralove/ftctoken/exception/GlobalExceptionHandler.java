package com.auroralove.ftctoken.exception;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auroralove.ftctoken.result.ResponseMessage;
import com.auroralove.ftctoken.result.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 全局异常补货类
 *
 * @author zyu
 * @date 2019/2/24
 */

@CrossOrigin
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    private static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseResult processException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        if (ex instanceof MissingServletRequestParameterException) {
            return new ResponseResult(400, ex);
        }
        if (ex instanceof RepeatLoginException) {
            LOGGER.error("=======" + ex.getMessage() + "=======");
            return new ResponseResult(ResponseMessage.REPEATLOGIN_EXCEPTION);
        }

        if (ex instanceof MissingTokenException) {
            LOGGER.error("=======" + ex.getMessage() + "=======");
            return new ResponseResult(ResponseMessage.MISS_TOKEN);
        }

        if (ex instanceof DuplicateKeyException) {
            LOGGER.error("=======违反主键约束：主键重复插入=======");
            return new ResponseResult(400, "主键重复插入！");
        }

        /**
         * 未知异常
         */
        LOGGER.error(ex.toString());
        return new ResponseResult(500, ex.getMessage());

    }

}
