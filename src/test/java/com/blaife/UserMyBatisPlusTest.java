package com.blaife;

import com.blaife.mapper.MyBatisPlusTestMapper;
import com.blaife.model.MyBaitsPlusTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMyBatisPlusTest {

    @Autowired
    private MyBatisPlusTestMapper myBatisPlusTestMapper;

    @Test
    public void testSelect(){
        List<MyBaitsPlusTest> userList = myBatisPlusTestMapper.selectList(null);
        System.out.println(userList);
    }

}
