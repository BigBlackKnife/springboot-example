package com.blaife;

import com.blaife.mapper.UserMapper;
import com.blaife.mapper.UserMapperT;
import com.blaife.model.MyBaitsPlusTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Mysql查询Users 数据表方法
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMapperT userMapperT;


    @Test
    public void testQuery() throws Exception {
        List<MyBaitsPlusTest> users = userMapper.getAll();
        System.out.println(users.toString());
    }

    @Test
    public void testQuery2() throws Exception {
        List<MyBaitsPlusTest> users = userMapperT.getAll();
        System.out.println(users.toString());
    }

}
