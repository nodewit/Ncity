package com.manager.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 评论表(t_comment)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-17
 */
@Entity
@Table(name = "t_comment")
public class CommentEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 5519841312442536321L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 1-美食评论  */
    @Column(name = "type", nullable = true, length = 10)
    private Integer type;

    /** 评论的对象ID */
    @Column(name = "object_id", nullable = true, length = 19)
    private Long objectId;

    /** 评论人uuid */
    @Column(name = "from_uuid", nullable = true, length = 36)
    private String fromUuid;

    /** 被评论的id */
    @Column(name = "to_id", nullable = true, length = 19)
    private Long toId;

    /** 评论内容 */
    @Column(name = "comment", nullable = true, length = 200)
    private String comment;

    /** 评论语音地址 */
    @Column(name = "voice", nullable = true, length = 100)
    private String voice;

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
     * 获取1-美食评论 
     * 
     * @return 1-美食评论 
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置1-美食评论 
     * 
     * @param type
     *          1-美食评论 
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
     * 获取评论人uuid
     * 
     * @return 评论人uuid
     */
    public String getFromUuid() {
        return this.fromUuid;
    }

    /**
     * 设置评论人uuid
     * 
     * @param fromUuid
     *          评论人uuid
     */
    public void setFromUuid(String fromUuid) {
        this.fromUuid = fromUuid;
    }

    /**
     * 获取被评论的id
     * 
     * @return 被评论的id
     */
    public Long getToId() {
        return this.toId;
    }

    /**
     * 设置被评论的id
     * 
     * @param toId
     *          被评论的id
     */
    public void setToId(Long toId) {
        this.toId = toId;
    }

    /**
     * 获取评论内容
     * 
     * @return 评论内容
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * 设置评论内容
     * 
     * @param comment
     *          评论内容
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取评论语音地址
     * 
     * @return 评论语音地址
     */
    public String getVoice() {
        return this.voice;
    }

    /**
     * 设置评论语音地址
     * 
     * @param voice
     *          评论语音地址
     */
    public void setVoice(String voice) {
        this.voice = voice;
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