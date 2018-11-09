/*
 * Welcome to use the TableGo Tools.
 * 
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 * 
 * Author:bianj
 * Email:edinsker@163.com
 * Version:5.8.8
 */

package com.ncity.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 常用类型表(t_type)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-12
 */
@Entity
@Table(name = "t_type")
public class TypeEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4938118291265293230L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 1-收入  2-支出  3-以物换物  4-小区安全发现 */
    @Column(name = "type", nullable = true, length = 10)
    private Integer type;

    /** 名称 */
    @Column(name = "name", nullable = true, length = 100)
    private String name;

    /** 创建人uuid */
    @Column(name = "uuid", nullable = true, length = 36)
    private String uuid;

    /** 创建时间 */
    @Column(name = "create_time", nullable = true, length = 19)
    private Long createTime;

    /** 修改时间 */
    @Column(name = "update_time", nullable = true, length = 19)
    private Long updateTime;

    /** 0 无效 1 有效 */
    @Column(name = "flag", nullable = true, length = 10)
    private Integer flag;

    /**
     * 获取ID
     * 
     * @return ID
     */
    public Long getId() {
        return this.id;
    }

    /**
     * 设置ID
     * 
     * @param id
     *          ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取1-收入  2-支出  3-以物换物  4-小区安全发现
     * 
     * @return 1-收入  2-支出  3-以物换物  4-小区安全发现
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置1-收入  2-支出  3-以物换物  4-小区安全发现
     * 
     * @param type
     *          1-收入  2-支出  3-以物换物  4-小区安全发现
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取名称
     * 
     * @return 名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置名称
     * 
     * @param name
     *          名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取创建人uuid
     * 
     * @return 创建人uuid
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * 设置创建人uuid
     * 
     * @param uuid
     *          创建人uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Long getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置创建时间
     * 
     * @param createTime
     *          创建时间
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     * 
     * @return 修改时间
     */
    public Long getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置修改时间
     * 
     * @param updateTime
     *          修改时间
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取0 无效 1 有效
     * 
     * @return 0 无效 1 有效
     */
    public Integer getFlag() {
        return this.flag;
    }

    /**
     * 设置0 无效 1 有效
     * 
     * @param flag
     *          0 无效 1 有效
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}