package com.manager.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 链存储表(t_chain)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-17
 */
@Entity
@Table(name = "t_chain")
public class ChainEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -13551951754267846L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 用户UUID */
    @Column(name = "uuid", nullable = true, length = 36)
    private String uuid;

    /** 区块链地址 */
    @Column(name = "address", nullable = true, length = 80)
    private String address;

    /** 1-公开  2-私密 */
    @Column(name = "type", nullable = true, length = 10)
    private Integer type;

    /** 对应常用类型表 */
    @Column(name = "type_id", nullable = true, length = 19)
    private Long typeId;

    /** 创建时间 */
    @Column(name = "create_time", nullable = true, length = 19)
    private Long createTime;

    /** 修改时间 */
    @Column(name = "update_time", nullable = true, length = 19)
    private Long updateTime;

    /** 0 无效 1 有效 */
    @Column(name = "flag", nullable = true, length = 10)
    private Integer flag;

    /** 保存在区块的数据 */
    @Transient
    private Object data;
    
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
     * 获取区块链地址
     * 
     * @return 区块链地址
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 设置区块链地址
     * 
     * @param address
     *          区块链地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取1-公开  2-私密
     * 
     * @return 1-公开  2-私密
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置1-公开  2-私密
     * 
     * @param type
     *          1-公开  2-私密
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
    
}