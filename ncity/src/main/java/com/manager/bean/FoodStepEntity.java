package com.manager.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 美食步骤表(t_food_step)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-17
 */
@Entity
@Table(name = "t_food_step")
public class FoodStepEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1698871386771908109L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 美食ID */
    @Column(name = "food_id", nullable = true, length = 19)
    private Long foodId;

    /** 描述 */
    @Column(name = "description", nullable = true, length = 300)
    private String description;

    /** 图片 */
    @Column(name = "photo_url", nullable = true, length = 100)
    private String photoUrl;

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
     * 获取美食ID
     * 
     * @return 美食ID
     */
    public Long getFoodId() {
        return this.foodId;
    }

    /**
     * 设置美食ID
     * 
     * @param foodId
     *          美食ID
     */
    public void setFoodId(Long foodId) {
        this.foodId = foodId;
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