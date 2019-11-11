package com.chenws.gateway.service;

/**
 * Created by chenws on 2019/11/1.
 */
public interface PathVerifyService {

    /**
     * 判断路径是否需要鉴权
     * @param path
     * @return
     */
    Boolean shouldFilter(String path);

}
