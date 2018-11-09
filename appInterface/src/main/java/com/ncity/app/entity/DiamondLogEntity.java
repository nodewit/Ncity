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

/**
 * 灵钻生成记录(t_diamond_log)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-18
 */
@Entity
@Table(name = "t_diamond_log")
public class DiamondLogEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -7920153190972441749L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 用户UUID */
    @Column(name = "uuid", nullable = true, length = 36)
    private String uuid;

    /** 事务ID */
    @Column(name = "transaction_id", nullable = true, length = 100)
    private String transactionId;

    /** 0-未领取  1-领取 */
    @Column(name = "receive", nullable = true, length = 10)
    private Integer receive;
    
    /** 1-收入  2-支出 */
    @Column(name = "type", nullable = true, length = 10)
    private Integer type;

    /** 灵钻量 */
    @Column(name = "diamond", nullable = true, length = 13)
    private Double diamond;

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
     * 获取事务ID
     * 
     * @return 事务ID
     */
    public String getTransactionId() {
        return this.transactionId;
    }

    /**
     * 设置事务ID
     * 
     * @param transactionId
     *          事务ID
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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
     * 获取灵钻量
     * 
     * @return 灵钻量
     */
    public Double getDiamond() {
        return this.diamond;
    }

    /**
     * 设置灵钻量
     * 
     * @param diamond
     *          灵钻量
     */
    public void setDiamond(Double diamond) {
        this.diamond = diamond;
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
    
	public Integer getReceive() {
		return receive;
	}

	public void setReceive(Integer receive) {
		this.receive = receive;
	}
}