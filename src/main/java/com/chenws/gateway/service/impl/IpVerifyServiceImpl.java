package com.chenws.gateway.service.impl;

import com.chenws.gateway.service.IpVerifyService;
import org.springframework.stereotype.Service;

/**
 * Created by chenws on 2019/11/1.
 */
@Service
public class IpVerifyServiceImpl implements IpVerifyService {

    @Override
    public Boolean isValid(String ip) {
        return true;
    }
}
