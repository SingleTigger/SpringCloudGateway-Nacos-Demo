package com.chenws.gateway.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Created by chenws on 2019/11/1.
 */
@Slf4j
public final class ObjectMapperUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper
                // 是否需要排序
                .configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
                // 忽略空bean转json的错误
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                // 取消默认转换timestamps形式
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                // 序列化的时候，过滤null属性
                .setSerializationInclusion(Include.NON_NULL)
                // 忽略在json字符串中存在，但在java对象中不存在对应属性的情况，防止错误
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                // 忽略空bean转json的错误
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    private ObjectMapperUtil() {

    }

    /**
     * Obj转Map
     * @param obj 对象
     * @param <T> 对象泛型
     * @return Map集合
     */
    public static <T> Map obj2Map(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return objectMapper.readValue(obj2Json(obj),Map.class);
        } catch (Exception e) {
            log.warn("对象解析为Map异常", e);
        }
        return null;
    }

    /**
     * 对象转json字符串
     *
     * @param obj 对象
     * @param <T> 对象泛型
     * @return json字符串
     */
    public static <T> String obj2Json(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("对象解析为json字符串异常", e);
        }
        return null;
    }

    /**
     * 对象转bytes
     *
     * @param obj 对象
     * @param <T> 对象泛型
     * @return json字符串
     */
    public static <T> byte[] obj2Bytes(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (Exception e) {
            log.warn("对象解析为byte数组异常", e);
        }
        return null;
    }

}

