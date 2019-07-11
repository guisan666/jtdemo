package com.jt.aspect;

import com.jt.vo.SysResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;


//@RestControllerAdvice   //针对controller层生效
@Slf4j
public class SysResultAspect {
    /**
     * 如果程序报错,则统一返回异常信息
     */
    @ExceptionHandler(RuntimeException.class)    //如果遇到指定的异常类型,执行下列方法
    public SysResult sysResultFail(Exception e){
        log.error("服务器异常~~~" + e.getMessage());
        return SysResult.fail();
    }
}
