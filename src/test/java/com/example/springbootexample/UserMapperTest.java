package com.example.springbootexample;

import com.example.springbootexample.test.mapper.UserMapper;
import com.example.springbootexample.test.mapper.UserMapperT;
import com.example.springbootexample.test.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMapperT userMapperT;


    @Test
    public void testQuery() throws Exception {
        List<User> users = userMapper.getAll();
        System.out.println(users.toString());
    }

    @Test
    public void testQuery2() throws Exception {
        List<User> users = userMapperT.getAll();
        System.out.println(users.toString());
    }

}
