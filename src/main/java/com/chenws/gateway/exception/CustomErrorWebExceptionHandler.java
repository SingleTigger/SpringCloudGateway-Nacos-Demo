package com.chenws.gateway.exception;

import com.chenws.gateway.utils.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;

import java.util.Map;

/**
 * 全局异常处理
 * @author chenws
 * @date 2019/12/19 17:09:05
 */
@Slf4j
public class CustomErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    private static final String STATUS = "status";

    public CustomErrorWebExceptionHandler(ErrorAttributes errorAttributes,
                                          ResourceProperties resourceProperties,
                                          ErrorProperties errorProperties,
                                          ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Throwable error = super.getError(request);
        //处理自定义异常
        if(error instanceof Exception){
            Exception appException = (Exception) error;
            //这里返回自定义的数据，存放到Map中，如msg->xxx,code->xxx,data->xxx
            Map<String,Object> errorAttributes = ObjectMapperUtil.obj2Map(appException);
            //自定义异常返回的http码为200
            errorAttributes.put(STATUS, HttpStatus.OK.value());
            return errorAttributes;
        }else{
            return super.getErrorAttributes(request,includeStackTrace);
        }
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 复写，主要是去掉自定义加入的status，不写到body中
     * @param errorAttributes 错误的信息
     * @return http 状态码
     */
    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        int statusCode = (int) errorAttributes.get(STATUS);
        errorAttributes.remove(STATUS);
        return statusCode;
    }



}
