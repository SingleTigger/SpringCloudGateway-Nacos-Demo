package com.chenws.gateway.service;

/**
 * Created by chenws on 2019/11/1.
 */
public interface IpVerifyService {

    /**
     * 校验ip是否为有效访问
     * @param ip ip
     * @return Boolean
     */
    Boolean isValid(String ip);
}
