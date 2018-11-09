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
 * 彩票表(t_lottery)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-17
 */
@Entity
@Table(name = "t_lottery")
public class LotteryEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4875074330906215564L;

    /** ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** 用户UUID */
    @Column(name = "uuid", nullable = true, length = 36)
    private String uuid;

    /** 彩票号码 */
    @Column(name = "number", nullable = true, length = 30)
    private String number;

    /** 0-等待出票  1-已出票 */
    @Column(name = "is_buy", nullable = true, length = 10)
    private Integer isBuy;

    /** 0-未中奖 1-中奖 */
    @Column(name = "is_winning", nullable = true, length = 10)
    private Integer isWinning;

    /** 中奖金额 */
    @Column(name = "money", nullable = true, length = 15)
    private BigDecimal money;

    /** 链块地址 */
    @Column(name = "chain_address", nullable = true, length = 100)
    private String chainAddress;

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
     * 获取彩票号码
     * 
     * @return 彩票号码
     */
    public String getNumber() {
        return this.number;
    }

    /**
     * 设置彩票号码
     * 
     * @param number
     *          彩票号码
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 获取0-等待出票  1-已出票
     * 
     * @return 0-等待出票  1-已出票
     */
    public Integer getIsBuy() {
        return this.isBuy;
    }

    /**
     * 设置0-等待出票  1-已出票
     * 
     * @param isBuy
     *          0-等待出票  1-已出票
     */
    public void setIsBuy(Integer isBuy) {
        this.isBuy = isBuy;
    }

    /**
     * 获取0-未中奖 1-中奖
     * 
     * @return 0-未中奖 1-中奖
     */
    public Integer getIsWinning() {
        return this.isWinning;
    }

    /**
     * 设置0-未中奖 1-中奖
     * 
     * @param isWinning
     *          0-未中奖 1-中奖
     */
    public void setIsWinning(Integer isWinning) {
        this.isWinning = isWinning;
    }

    /**
     * 获取中奖金额
     * 
     * @return 中奖金额
     */
    public BigDecimal getMoney() {
        return this.money;
    }

    /**
     * 设置中奖金额
     * 
     * @param money
     *          中奖金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取链块地址
     * 
     * @return 链块地址
     */
    public String getChainAddress() {
        return this.chainAddress;
    }

    /**
     * 设置链块地址
     * 
     * @param chainAddress
     *          链块地址
     */
    public void setChainAddress(String chainAddress) {
        this.chainAddress = chainAddress;
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