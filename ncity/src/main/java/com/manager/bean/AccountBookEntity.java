package com.manager.bean;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 账本记录(t_account_book)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-17
 */
@Entity
@Table(name = "t_account_book")
public class AccountBookEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8426163896812862533L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 1-收入  2-支出 */
    @Column(name = "type", nullable = true, length = 10)
    private Integer type;

    /** 对应常用类型表 */
    @Column(name = "type_id", nullable = true, length = 19)
    private Long typeId;

    /** 金额 */
    @Column(name = "money", nullable = true, length = 15)
    private BigDecimal money;

    /** 图片 */
    @Column(name = "photo_url", nullable = true, length = 100)
    private String photoUrl;

    /** 创建人uuid */
    @Column(name = "uuid", nullable = true, length = 36)
    private String uuid;

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
     * 获取1-收入  2-支出
     * 
     * @return 1-收入  2-支出
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置1-收入  2-支出
     * 
     * @param type
     *          1-收入  2-支出
     */
    public void setType(Integer type) {
        this.type = type;
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
     * 获取金额
     * 
     * @return 金额
     */
    public BigDecimal getMoney() {
        return this.money;
    }

    /**
     * 设置金额
     * 
     * @param money
     *          金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
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
     * 获取创建人uuid
     * 
     * @return 创建人uuid
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * 设置创建人uuid
     * 
     * @param uuid
     *          创建人uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
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