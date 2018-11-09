package com.manager.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 物品表(t_goods)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-17
 */
@Entity
@Table(name = "t_goods")
public class GoodsEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 7279759386927932658L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 标题 */
    @Column(name = "title", nullable = true, length = 50)
    private String title;

    /** 对应的常用类型表 */
    @Column(name = "type_id", nullable = true, length = 19)
    private Long typeId;

    /** 物品描述 */
    @Column(name = "description", nullable = true, length = 300)
    private String description;

    /** 图片 */
    @Column(name = "photo_url", nullable = true, length = 100)
    private String photoUrl;

    /** 想交换的物品 */
    @Column(name = "exchange_goods", nullable = true, length = 300)
    private String exchangeGoods;

    /** 详细地址 */
    @Column(name = "address", nullable = true, length = 100)
    private String address;

    /** 联系电话 */
    @Column(name = "mobile", nullable = true, length = 15)
    private String mobile;

    /** 0-未审核  1-通过  2-不通过 */
    @Column(name = "verify", nullable = true, length = 10)
    private Integer verify;

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
     * 获取对应的常用类型表
     * 
     * @return 对应的常用类型表
     */
    public Long getTypeId() {
        return this.typeId;
    }

    /**
     * 设置对应的常用类型表
     * 
     * @param typeId
     *          对应的常用类型表
     */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    /**
     * 获取物品描述
     * 
     * @return 物品描述
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 设置物品描述
     * 
     * @param description
     *          物品描述
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
     * 获取想交换的物品
     * 
     * @return 想交换的物品
     */
    public String getExchangeGoods() {
        return this.exchangeGoods;
    }

    /**
     * 设置想交换的物品
     * 
     * @param exchangeGoods
     *          想交换的物品
     */
    public void setExchangeGoods(String exchangeGoods) {
        this.exchangeGoods = exchangeGoods;
    }

    /**
     * 获取详细地址
     * 
     * @return 详细地址
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 设置详细地址
     * 
     * @param address
     *          详细地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取联系电话
     * 
     * @return 联系电话
     */
    public String getMobile() {
        return this.mobile;
    }

    /**
     * 设置联系电话
     * 
     * @param mobile
     *          联系电话
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
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