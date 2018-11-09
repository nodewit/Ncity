package com.manager.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 版本更新表(t_version)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-17
 */
@Entity
@Table(name = "t_version")
public class VersionEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -3446352480945995291L;

    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 客户端设备类型(2 Android/1 IOS) */
    @Column(name = "client_device_type", nullable = true, length = 10)
    private Integer clientDeviceType;

    /** 升级下载网址 */
    @Column(name = "download_url", nullable = true, length = 255)
    private String downloadUrl;

    /** 记录本次版本应该升级到最新版本号 */
    @Column(name = "update_id", nullable = true, length = 10)
    private Integer updateId;

    /** 该升级版本的描述信息 */
    @Column(name = "update_info", nullable = true, length = 4000)
    private String updateInfo;

    /** 是否强制安装 */
    @Column(name = "update_install", nullable = true, length = 10)
    private Integer updateInstall;

    /** 版本号 */
    @Column(name = "version", nullable = true, length = 20)
    private String version;

    /** 1 已上线  0 未上线 */
    @Column(name = "is_online", nullable = true, length = 10)
    private Integer isOnline;

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
     * 获取id
     * 
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * 设置id
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取客户端设备类型(2 Android/1 IOS)
     * 
     * @return 客户端设备类型(2 Android/1 IOS)
     */
    public Integer getClientDeviceType() {
        return this.clientDeviceType;
    }

    /**
     * 设置客户端设备类型(2 Android/1 IOS)
     * 
     * @param clientDeviceType
     *          客户端设备类型(2 Android/1 IOS)
     */
    public void setClientDeviceType(Integer clientDeviceType) {
        this.clientDeviceType = clientDeviceType;
    }

    /**
     * 获取升级下载网址
     * 
     * @return 升级下载网址
     */
    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    /**
     * 设置升级下载网址
     * 
     * @param downloadUrl
     *          升级下载网址
     */
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    /**
     * 获取记录本次版本应该升级到最新版本号
     * 
     * @return 记录本次版本应该升级到最新版本号
     */
    public Integer getUpdateId() {
        return this.updateId;
    }

    /**
     * 设置记录本次版本应该升级到最新版本号
     * 
     * @param updateId
     *          记录本次版本应该升级到最新版本号
     */
    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    /**
     * 获取该升级版本的描述信息
     * 
     * @return 该升级版本的描述信息
     */
    public String getUpdateInfo() {
        return this.updateInfo;
    }

    /**
     * 设置该升级版本的描述信息
     * 
     * @param updateInfo
     *          该升级版本的描述信息
     */
    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    /**
     * 获取是否强制安装
     * 
     * @return 是否强制安装
     */
    public Integer getUpdateInstall() {
        return this.updateInstall;
    }

    /**
     * 设置是否强制安装
     * 
     * @param updateInstall
     *          是否强制安装
     */
    public void setUpdateInstall(Integer updateInstall) {
        this.updateInstall = updateInstall;
    }

    /**
     * 获取版本号
     * 
     * @return 版本号
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * 设置版本号
     * 
     * @param version
     *          版本号
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 获取1 已上线  0 未上线
     * 
     * @return 1 已上线  0 未上线
     */
    public Integer getIsOnline() {
        return this.isOnline;
    }

    /**
     * 设置1 已上线  0 未上线
     * 
     * @param isOnline
     *          1 已上线  0 未上线
     */
    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
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