package com.blaife.controller;

import com.blaife.mapper.UsersBaseMapper;
import com.blaife.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/baseMapper")
public class UsersBaseMapperController {

    @Autowired
    @SuppressWarnings(value = "all")
    private UsersBaseMapper user;

    /**
     * 单条数据添加
     * @param name
     * @param age
     * @return
     */
    @RequestMapping("/insert")
    public String insert(String name, Integer age) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Users usersModel = new Users();
        usersModel.setId(uuid);
        usersModel.setName(name);
        usersModel.setAge(age);
        int r = user.insert(usersModel);
        if (r > 0) {
            return "添加用户"+ name +"成功，uuid为" + uuid;
        } else {
            return "添加失败";
        }
    }

    /**
     * 根据id删除用户
     * @param uuid
     * @return
     */
    @RequestMapping("/deleteByUUID")
    public String deleteByUUID(String uuid) {
        int r = user.deleteById(uuid);
        if (r > 0) {
            return "删除用户成功，uuid为" + uuid;
        } else {
            return "删除失败";
        }
    }

    /**
     * 根据用户Id修改数据
     * @return
     */
    @RequestMapping("/updateByUUID")
    public String updateByUUID(String uuid) {
        Users usersModel = new Users();
        usersModel.setId(uuid);
        usersModel.setName("lisex");
        usersModel.setAge(18);
        int r = user.updateById(usersModel);
        if (r > 0) {
            return "修改用户信息成功，uuid为" + uuid;
        } else {
            return "修改失败";
        }
    }

    /**
     * 获取users表中的所有数据
     * @return
     */
    @RequestMapping("/allUsers")
    public List<Users> getAllUsers() {
        return user.selectList(null);
    }

}
