package com.manager.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * APP配置表(t_app_parameter)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-17
 */
@Entity
@Table(name = "t_app_parameter")
public class AppParameterEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4982658662339405662L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 字段名称 */
    @Column(name = "field", nullable = true, length = 50)
    private String field;

    /** 1 值 2 图片 */
    @Column(name = "type", nullable = true, length = 10)
    private Integer type;

    /** 备注 */
    @Column(name = "remark", nullable = true, length = 500)
    private String remark;

    /** 值 */
    @Column(name = "value", nullable = true, length = 1000)
    private String value;

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
     * 获取字段名称
     * 
     * @return 字段名称
     */
    public String getField() {
        return this.field;
    }

    /**
     * 设置字段名称
     * 
     * @param field
     *          字段名称
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * 获取1 值 2 图片
     * 
     * @return 1 值 2 图片
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置1 值 2 图片
     * 
     * @param type
     *          1 值 2 图片
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取备注
     * 
     * @return 备注
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置备注
     * 
     * @param remark
     *          备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取值
     * 
     * @return 值
     */
    public String getValue() {
        return this.value;
    }

    /**
     * 设置值
     * 
     * @param value
     *          值
     */
    public void setValue(String value) {
        this.value = value;
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