package com.blaife.mapper.master;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blaife.model.MyBaitsPlusTest;

/**
 * 查询数据基于实体类 即 BaseMapper<MyBaitsPlusTest>
 * 此实体类中注解已经判断了表和字段，在其他地方调用即可
 *
 * 并且MyBatis-Plus 无缝支持MyBatis的内容
 * 引入依赖时只引入MyBaits-Plus即可
 */
public interface MyBatisPlusTestMapper extends BaseMapper<MyBaitsPlusTest> {

}
