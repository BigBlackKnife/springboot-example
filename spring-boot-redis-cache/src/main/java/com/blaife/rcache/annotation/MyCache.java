package com.blaife.rcache.annotation;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;

import java.lang.annotation.*;

@Caching(
        put = {
                @CachePut(key = "#datas.get(0)"),
                @CachePut(key = "#datas.get(1)")
        }
)
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MyCache {
}
