package com.manager.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 美食表(t_food)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-17
 */
@Entity
@Table(name = "t_food")
public class FoodEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 3281609071195518169L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 标题 */
    @Column(name = "title", nullable = true, length = 100)
    private String title;

    /** 1-早餐 2-午餐 3-晚餐 */
    @Column(name = "type", nullable = true, length = 10)
    private Integer type;

    /** 描述 */
    @Column(name = "description", nullable = true, length = 200)
    private String description;

    /** 封面 */
    @Column(name = "photo_url", nullable = true, length = 100)
    private String photoUrl;

    /** 用户UUID */
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
     * 获取1-早餐 2-午餐 3-晚餐
     * 
     * @return 1-早餐 2-午餐 3-晚餐
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置1-早餐 2-午餐 3-晚餐
     * 
     * @param type
     *          1-早餐 2-午餐 3-晚餐
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取描述
     * 
     * @return 描述
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 设置描述
     * 
     * @param description
     *          描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取封面
     * 
     * @return 封面
     */
    public String getPhotoUrl() {
        return this.photoUrl;
    }

    /**
     * 设置封面
     * 
     * @param photoUrl
     *          封面
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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