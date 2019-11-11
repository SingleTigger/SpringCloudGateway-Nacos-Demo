package com.chenws.gateway.service.impl;

import com.chenws.gateway.service.PathVerifyService;
import org.springframework.stereotype.Service;

/**
 * Created by chenws on 2019/11/1.
 */
@Service
public class PathVerifyServiceImpl implements PathVerifyService {

    @Override
    public Boolean shouldFilter(String path) {
        return true;
    }
}
