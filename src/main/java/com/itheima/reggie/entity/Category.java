package com.itheima.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分类
 */
@Data
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    private Integer type;



    private String name;


    private Integer sort;


     //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;



    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;



    @TableField(fill = FieldFill.INSERT)
    private Long createUser;



    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;



//    private Integer isDeleted;

}
