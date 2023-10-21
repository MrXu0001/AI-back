package com.aicanvas.aicanvas.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户表(User)实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("api_user")
public class User implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    
    /**
    * 主键
    */
    @TableId
    private Long id;
    /**
    * 用户名
    */
    private String userName;
    /**
    * 密码
    */
    private String password;
    /**
    * 账号状态（0正常 1停用）
    */
    private String status;
    /**
    * 创建时间
    */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    /**
    * 更新时间
    */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtUpdate;
    /**
    * 删除标志（0代表未删除，1代表已删除）
    */
    @TableLogic
    private Integer isDelete;
}