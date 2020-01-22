package com.blaife.rcache;

import com.blaife.rcache.service.StringService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {

    @Autowired
    StringService stringService;

    /**
     * 测试数据修改
     */
    @Test
    public void testPut() {
        String result = stringService.testPut("blaife");
        System.out.println(result);
    }

    /**
     * 测试数据删除
     */
    @Test
    public void testEvict() {
        String result = stringService.testEvict("blaife");
        System.out.println(result);
    }

    /**
     * 测试数据缓存
     */
    @Test
    public void testAble() {
        String result = stringService.testAble("blaife");
        System.out.println(result);
    }

    @Test
    public void testPuts() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("test1");
        list.add("test2");
        List<String> results = stringService.testPuts(list);
        System.out.println(results);
    }

    @Test
    public void testEvicts() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("test1");
        list.add("test2");
        List<String> results = stringService.testEvicts(list);
        System.out.println(results);
    }
}
