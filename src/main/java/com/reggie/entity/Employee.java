package com.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工实体类
 */
@Data //自动整合Setter getter tostring 方法。
public class Employee implements Serializable { //Serializable 实现序列化
    private static final long serialVersionUID = 1L;
//    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String username;

    private String name;

    private String password;

    private String phone;

    private String sex;

    private String idNumber;

    private Integer status;

    /**
     * 加入注解，定义哪些字段为公共字段，并定义处理策略
     * fill: 自动填充策略
     * DEFAULT	默认不处理
     * INSERT	插入填充字段
     * UPDATE	更新填充字段
     * INSERT_UPDATE	插入和更新填充字段
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
