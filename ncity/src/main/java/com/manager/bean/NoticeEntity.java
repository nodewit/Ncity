package com.manager.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 通知表(t_notice)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-17
 */
@Entity
@Table(name = "t_notice")
public class NoticeEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -5200032736257242596L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 1-纯文本 2-富文本 */
    @Column(name = "type", nullable = true, length = 10)
    private Integer type;

    /** 标题 */
    @Column(name = "title", nullable = true, length = 50)
    private String title;

    /** 简介 */
    @Column(name = "introduction", nullable = true, length = 500)
    private String introduction;

    /** 内容 */
    @Column(name = "content", nullable = true, length = 2147483647)
    private String content;

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
     * 获取1-纯文本 2-富文本
     * 
     * @return 1-纯文本 2-富文本
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置1-纯文本 2-富文本
     * 
     * @param type
     *          1-纯文本 2-富文本
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取标题
     * 
     * @return 标题
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * 设置标题
     * 
     * @param title
     *          标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取简介
     * 
     * @return 简介
     */
    public String getIntroduction() {
        return this.introduction;
    }

    /**
     * 设置简介
     * 
     * @param introduction
     *          简介
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * 获取内容
     * 
     * @return 内容
     */
    public String getContent() {
        return this.content;
    }

    /**
     * 设置内容
     * 
     * @param content
     *          内容
     */
    public void setContent(String content) {
        this.content = content;
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