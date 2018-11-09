package com.manager.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户认证小区(t_approve_community)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-17
 */
@Entity
@Table(name = "t_approve_community")
public class ApproveCommunityEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 2662668629671722022L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 用户UUID */
    @Column(name = "uuid", nullable = true, length = 36)
    private String uuid;

    /** 小区ID */
    @Column(name = "community_id", nullable = true, length = 19)
    private Long communityId;

    /** 小区几期 */
    @Column(name = "period", nullable = true, length = 10)
    private Integer period;

    /** 小区单元室 */
    @Column(name = "unit", nullable = true, length = 10)
    private Integer unit;

    /** 房间号 */
    @Column(name = "room", nullable = true, length = 10)
    private Integer room;

    /** 1-业主本人 2-家人 3-租户 */
    @Column(name = "type", nullable = true, length = 10)
    private Integer type;

    /** 理由 */
    @Column(name = "reason", nullable = true, length = 200)
    private String reason;

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
     * 获取小区ID
     * 
     * @return 小区ID
     */
    public Long getCommunityId() {
        return this.communityId;
    }

    /**
     * 设置小区ID
     * 
     * @param communityId
     *          小区ID
     */
    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    /**
     * 获取小区几期
     * 
     * @return 小区几期
     */
    public Integer getPeriod() {
        return this.period;
    }

    /**
     * 设置小区几期
     * 
     * @param period
     *          小区几期
     */
    public void setPeriod(Integer period) {
        this.period = period;
    }

    /**
     * 获取小区单元室
     * 
     * @return 小区单元室
     */
    public Integer getUnit() {
        return this.unit;
    }

    /**
     * 设置小区单元室
     * 
     * @param unit
     *          小区单元室
     */
    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    /**
     * 获取房间号
     * 
     * @return 房间号
     */
    public Integer getRoom() {
        return this.room;
    }

    /**
     * 设置房间号
     * 
     * @param room
     *          房间号
     */
    public void setRoom(Integer room) {
        this.room = room;
    }

    /**
     * 获取1-业主本人 2-家人 3-租户
     * 
     * @return 1-业主本人 2-家人 3-租户
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置1-业主本人 2-家人 3-租户
     * 
     * @param type
     *          1-业主本人 2-家人 3-租户
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取理由
     * 
     * @return 理由
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * 设置理由
     * 
     * @param reason
     *          理由
     */
    public void setReason(String reason) {
        this.reason = reason;
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