package com.blaife;

import com.blaife.test.mapper.UserMapper;
import com.blaife.test.mapper.UserMapperT;
import com.blaife.test.model.User;
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
