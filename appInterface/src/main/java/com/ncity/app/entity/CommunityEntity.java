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

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 小区信息表(t_community)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-12
 */
@Entity
@Table(name = "t_community")
public class CommunityEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 8593539567130939511L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 小区名称 */
    @Column(name = "name", nullable = true, length = 50)
    private String name;

    /** 小区详细地址 */
    @Column(name = "address", nullable = true, length = 200)
    private String address;

    /** 经度 */
    @Column(name = "lng", nullable = true, length = 15)
    private BigDecimal lng;

    /** 纬度 */
    @Column(name = "lat", nullable = true, length = 15)
    private BigDecimal lat;

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
     * 获取小区名称
     * 
     * @return 小区名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置小区名称
     * 
     * @param name
     *          小区名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取小区详细地址
     * 
     * @return 小区详细地址
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 设置小区详细地址
     * 
     * @param address
     *          小区详细地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取经度
     * 
     * @return 经度
     */
    public BigDecimal getLng() {
        return this.lng;
    }

    /**
     * 设置经度
     * 
     * @param lng
     *          经度
     */
    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    /**
     * 获取纬度
     * 
     * @return 纬度
     */
    public BigDecimal getLat() {
        return this.lat;
    }

    /**
     * 设置纬度
     * 
     * @param lat
     *          纬度
     */
    public void setLat(BigDecimal lat) {
        this.lat = lat;
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