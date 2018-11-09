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

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户表(t_app_user)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-12
 */
@ApiModel
@Entity
@Table(name = "t_app_user")
public class AppUserEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -1848867008808046321L;

    /** 主键 */
    @ApiModelProperty(hidden = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 19)
    private Long id;

    /** app访问接口的用户ID */
    @ApiModelProperty(value = "用户UUID", required = false, example="用户UUID")
    @Column(name = "UUID", nullable = true, length = 36)
    private String uuid;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", required = true, example="姓名")
    @Column(name = "name", nullable = true, length = 10)
    private String name;

    /** 身份证 */
    @ApiModelProperty(value = "身份证", required = true, example="身份证")
    @Column(name = "idcard", nullable = true, length = 20)
    private String idcard;

    /** 微信openid--unionId */
    @ApiModelProperty(value = "微信openid", required = false, example="微信openid")
    @Column(name = "openid", nullable = true, length = 36)
    private String openid;

    /** 手机号 */
    @ApiModelProperty(value = "手机号", required = true, example="手机号")
    @Column(name = "mobile", nullable = true, length = 12)
    private String mobile;

    /** 邀请码 */
    @ApiModelProperty(value = "邀请码", required = false, example="邀请码")
    @Column(name = "invitation_code", nullable = true, length = 6)
    private String invitationCode;

    /** 头像 */
    @ApiModelProperty(value = "头像", required = false, example="头像")
    @Column(name = "head_url", nullable = true, length = 100)
    private String headUrl;

    /** 公钥 */
    @ApiModelProperty(hidden = true)
    @Column(name = "public_key", nullable = true, length = 100)
    private String publicKey;

    /** 私钥 */
    @ApiModelProperty(hidden = true)
    @Column(name = "private_key", nullable = true, length = 100)
    private String privateKey;
    
    /** 初始资产地址 */
    @ApiModelProperty(hidden = true)
    @Column(name = "init_address", nullable = true, length = 100)
    private String initAddress;

    /** 灵钻 */
    @ApiModelProperty(value = "灵钻", required = false, example="灵钻")
    @Column(name = "diamond", nullable = true, length = 15)
    private BigDecimal diamond;

    /** 灵力 */
    @ApiModelProperty(value = "灵力", required = false, example="灵力")
    @Column(name = "integral", nullable = true, length = 10)
    private Integer integral;

    /** 账号锁过期时间 */
    @ApiModelProperty(hidden = true)
    @Column(name = "lock_time", nullable = true, length = 19)
    private Long lockTime;

    /** 注册时间 */
    @ApiModelProperty(hidden = true)
    @Column(name = "registered_time", nullable = true, length = 19)
    private Long registeredTime;

    /** 0 无效 1 有效 */
    @ApiModelProperty(hidden = true)
    @Column(name = "flag", nullable = true, length = 10)
    private Integer flag;

    /**
     * 获取主键
     * 
     * @return 主键
     */
    public Long getId() {
        return this.id;
    }

    /**
     * 设置主键
     * 
     * @param id
     *          主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取app访问接口的用户ID
     * 
     * @return app访问接口的用户ID
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * 设置app访问接口的用户ID
     * 
     * @param uuid
     *          app访问接口的用户ID
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取姓名
     * 
     * @return 姓名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置姓名
     * 
     * @param name
     *          姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取身份证
     * 
     * @return 身份证
     */
    public String getIdcard() {
        return this.idcard;
    }

    /**
     * 设置身份证
     * 
     * @param idcard
     *          身份证
     */
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    /**
     * 获取微信openid
     * 
     * @return 微信openid
     */
    public String getOpenid() {
        return this.openid;
    }

    /**
     * 设置微信openid
     * 
     * @param openid
     *          微信openid
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * 获取手机号
     * 
     * @return 手机号
     */
    public String getMobile() {
        return this.mobile;
    }

    /**
     * 设置手机号
     * 
     * @param mobile
     *          手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取邀请码
     * 
     * @return 邀请码
     */
    public String getInvitationCode() {
        return this.invitationCode;
    }

    /**
     * 设置邀请码
     * 
     * @param invitationCode
     *          邀请码
     */
    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    /**
     * 获取头像
     * 
     * @return 头像
     */
    public String getHeadUrl() {
        return this.headUrl;
    }

    /**
     * 设置头像
     * 
     * @param headUrl
     *          头像
     */
    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    /**
     * 获取公钥
     * 
     * @return 公钥
     */
    public String getPublicKey() {
        return this.publicKey;
    }

    /**
     * 设置公钥
     * 
     * @param publicKey
     *          公钥
     */
    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * 获取私钥
     * 
     * @return 私钥
     */
    public String getPrivateKey() {
        return this.privateKey;
    }

    /**
     * 设置私钥
     * 
     * @param privateKey
     *          私钥
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * 获取灵钻
     * 
     * @return 灵钻
     */
    public BigDecimal getDiamond() {
        return this.diamond;
    }

    /**
     * 设置灵钻
     * 
     * @param diamond
     *          灵钻
     */
    public void setDiamond(BigDecimal diamond) {
        this.diamond = diamond;
    }

    /**
     * 获取灵力
     * 
     * @return 灵力
     */
    public Integer getIntegral() {
        return this.integral;
    }

    /**
     * 设置灵力
     * 
     * @param integral
     *          灵力
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    /**
     * 获取账号锁过期时间
     * 
     * @return 账号锁过期时间
     */
    public Long getLockTime() {
        return this.lockTime;
    }

    /**
     * 设置账号锁过期时间
     * 
     * @param lockTime
     *          账号锁过期时间
     */
    public void setLockTime(Long lockTime) {
        this.lockTime = lockTime;
    }

    /**
     * 获取注册时间
     * 
     * @return 注册时间
     */
    public Long getRegisteredTime() {
        return this.registeredTime;
    }

    /**
     * 设置注册时间
     * 
     * @param registeredTime
     *          注册时间
     */
    public void setRegisteredTime(Long registeredTime) {
        this.registeredTime = registeredTime;
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

	public String getInitAddress() {
		return initAddress;
	}

	public void setInitAddress(String initAddress) {
		this.initAddress = initAddress;
	}
    
    
}