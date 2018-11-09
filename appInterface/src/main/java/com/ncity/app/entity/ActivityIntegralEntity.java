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
import javax.persistence.Transient;

/**
 * 活动灵力表(t_activity_integral)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-12
 */
@Entity
@Table(name = "t_activity_integral")
public class ActivityIntegralEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4561409504022502800L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 用户UUID */
    @Column(name = "uuid", nullable = true, length = 36)
    private String uuid;

    /** 1-签到 2-邀请好友 3-新用户注册 4-发布时光机 5-发布一诺千金 6-点赞 7-评论 */
    @Column(name = "type", nullable = true, length = 10)
    private Integer type;
    
    /** 1-时光机 2-一诺千金  */
    @Column(name = "type_id", nullable = true, length = 10)
    private Long typeId;
    
    /** 对象ID  */
    @Column(name = "object_id", nullable = true, length = 10)
    private Long objectId;

    /** 灵力 */
    @Column(name = "integral", nullable = true, length = 10)
    private Integer integral;

    /** 创建时间 */
    @Column(name = "create_time", nullable = true, length = 19)
    private Long createTime;

    /** 修改时间 */
    @Column(name = "update_time", nullable = true, length = 19)
    private Long updateTime;

    /** 0 无效 1 有效 */
    @Column(name = "flag", nullable = true, length = 10)
    private Integer flag;

    @Transient
    private String typeName;
    
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
     * 获取1-签到 2-邀请好友
     * 
     * @return 1-签到 2-邀请好友
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置1-签到 2-邀请好友
     * 
     * @param type
     *          1-签到 2-邀请好友
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取灵力
     * 
     * @return 灵力
     */
    public Integer getIntegral() {
        return this.integral;
    }

    /**
     * 设置灵力
     * 
     * @param integral
     *          灵力
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
    
}