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
 * 用户行为表(t_activity_log)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-12
 */
@Entity
@Table(name = "t_activity_log")
public class ActivityLogEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4685239695085551544L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 用户uuid */
    @Column(name = "uuid", nullable = true, length = 36)
    private String uuid;

    /** 1-美食 */
    @Column(name = "type", nullable = true, length = 10)
    private Integer type;

    /** 评论的对象ID */
    @Column(name = "object_id", nullable = true, length = 19)
    private Long objectId;

    /** 1-点赞 2-收藏 */
    @Column(name = "activity_type", nullable = true, length = 10)
    private Integer activityType;

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
     * 获取用户uuid
     * 
     * @return 用户uuid
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * 设置用户uuid
     * 
     * @param uuid
     *          用户uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取1-美食
     * 
     * @return 1-美食
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置1-美食
     * 
     * @param type
     *          1-美食
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取评论的对象ID
     * 
     * @return 评论的对象ID
     */
    public Long getObjectId() {
        return this.objectId;
    }

    /**
     * 设置评论的对象ID
     * 
     * @param objectId
     *          评论的对象ID
     */
    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    /**
     * 获取1-点赞 2-收藏
     * 
     * @return 1-点赞 2-收藏
     */
    public Integer getActivityType() {
        return this.activityType;
    }

    /**
     * 设置1-点赞 2-收藏
     * 
     * @param activityType
     *          1-点赞 2-收藏
     */
    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
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