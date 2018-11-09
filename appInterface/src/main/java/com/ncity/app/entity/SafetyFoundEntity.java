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
 * 小区安全发现信息表(t_safety_found)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-12
 */
@Entity
@Table(name = "t_safety_found")
public class SafetyFoundEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1665708996846023861L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 地址 */
    @Column(name = "address", nullable = true, length = 100)
    private String address;

    /** 安全问题 */
    @Column(name = "description", nullable = true, length = 300)
    private String description;

    /** 对应常用类型表 */
    @Column(name = "type_id", nullable = true, length = 19)
    private Long typeId;

    /** 图片 */
    @Column(name = "photo_url", nullable = true, length = 300)
    private String photoUrl;

    /** 创建人UUID */
    @Column(name = "uuid", nullable = true, length = 36)
    private String uuid;

    /** 0-未审核  1-通过  2-不通过 */
    @Column(name = "verify", nullable = true, length = 10)
    private Integer verify;

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
     * 获取地址
     * 
     * @return 地址
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 设置地址
     * 
     * @param address
     *          地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取安全问题
     * 
     * @return 安全问题
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 设置安全问题
     * 
     * @param description
     *          安全问题
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取对应常用类型表
     * 
     * @return 对应常用类型表
     */
    public Long getTypeId() {
        return this.typeId;
    }

    /**
     * 设置对应常用类型表
     * 
     * @param typeId
     *          对应常用类型表
     */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    /**
     * 获取图片
     * 
     * @return 图片
     */
    public String getPhotoUrl() {
        return this.photoUrl;
    }

    /**
     * 设置图片
     * 
     * @param photoUrl
     *          图片
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * 获取创建人UUID
     * 
     * @return 创建人UUID
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * 设置创建人UUID
     * 
     * @param uuid
     *          创建人UUID
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取0-未审核  1-通过  2-不通过
     * 
     * @return 0-未审核  1-通过  2-不通过
     */
    public Integer getVerify() {
        return this.verify;
    }

    /**
     * 设置0-未审核  1-通过  2-不通过
     * 
     * @param verify
     *          0-未审核  1-通过  2-不通过
     */
    public void setVerify(Integer verify) {
        this.verify = verify;
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