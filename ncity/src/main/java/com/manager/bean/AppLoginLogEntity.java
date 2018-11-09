package com.manager.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 登录日志表(t_app_login_log)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-17
 */
@Entity
@Table(name = "t_app_login_log")
public class AppLoginLogEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -2425859901842939920L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 用户UUID */
    @Column(name = "uuid", nullable = true, length = 36)
    private String uuid;

    /** 唯一标识 */
    @Column(name = "token", nullable = true, length = 36)
    private String token;

    /** 过期时间 */
    @Column(name = "expired_time", nullable = true, length = 19)
    private Long expiredTime;

    /** 网络类型 1 wifi  2  流量 */
    @Column(name = "net_type", nullable = true, length = 10)
    private Integer netType;

    /** 设备类型 1 ios 2 android */
    @Column(name = "plantform", nullable = true, length = 10)
    private Integer plantform;

    /** 渠道ID */
    @Column(name = "cannel_id", nullable = true, length = 36)
    private String cannelId;

    /** 版本号 */
    @Column(name = "app_version", nullable = true, length = 36)
    private String appVersion;

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
     * 获取用户UUID
     * 
     * @return 用户UUID
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * 设置用户UUID
     * 
     * @param uuid
     *          用户UUID
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取唯一标识
     * 
     * @return 唯一标识
     */
    public String getToken() {
        return this.token;
    }

    /**
     * 设置唯一标识
     * 
     * @param token
     *          唯一标识
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取过期时间
     * 
     * @return 过期时间
     */
    public Long getExpiredTime() {
        return this.expiredTime;
    }

    /**
     * 设置过期时间
     * 
     * @param expiredTime
     *          过期时间
     */
    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    /**
     * 获取网络类型 1 wifi  2  流量
     * 
     * @return 网络类型 1 wifi  2  流量
     */
    public Integer getNetType() {
        return this.netType;
    }

    /**
     * 设置网络类型 1 wifi  2  流量
     * 
     * @param netType
     *          网络类型 1 wifi  2  流量
     */
    public void setNetType(Integer netType) {
        this.netType = netType;
    }

    /**
     * 获取设备类型 1 ios 2 android
     * 
     * @return 设备类型 1 ios 2 android
     */
    public Integer getPlantform() {
        return this.plantform;
    }

    /**
     * 设置设备类型 1 ios 2 android
     * 
     * @param plantform
     *          设备类型 1 ios 2 android
     */
    public void setPlantform(Integer plantform) {
        this.plantform = plantform;
    }

    /**
     * 获取渠道ID
     * 
     * @return 渠道ID
     */
    public String getCannelId() {
        return this.cannelId;
    }

    /**
     * 设置渠道ID
     * 
     * @param cannelId
     *          渠道ID
     */
    public void setCannelId(String cannelId) {
        this.cannelId = cannelId;
    }

    /**
     * 获取版本号
     * 
     * @return 版本号
     */
    public String getAppVersion() {
        return this.appVersion;
    }

    /**
     * 设置版本号
     * 
     * @param appVersion
     *          版本号
     */
    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
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