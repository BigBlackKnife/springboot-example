package com.blaife.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * mybatis-plus 注解
 * @TableName : 表名注解
 * @TableId : 主键注解
 *      idType: AUTO	数据库自增
 *              INPUT	自行输入
 *              ID_WORKER	分布式全局唯一ID 长整型类型
 *              UUID	32位UUID字符串
 *              NONE	无状态
 *              ID_WORKER_STR	分布式全局唯一ID 字符串类型
 *
 * @TableField : 字段注解(非主键) (属性极多，官网详看)
 * @Version ：乐观锁注解、标记 @Verison 在字段上
 * @EnumValue ： 通枚举类注解(注解在枚举字段上)
 * @TableLogic ： 描述：表字段逻辑处理注解（逻辑删除）
 * @SqlParser ： 租户注解 （不理解）
 */
@Data
@TableName("users")
public class MyBaitsPlusTest implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    @TableField("userName")
    private String userName;
    @TableField("passWord")
    private String passWord;


}
