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
 * 菜单表(t_menu)
 * 
 * @author 艾克
 * @version 1.0.0 2018-10-12
 */
@Entity
@Table(name = "t_menu")
public class MenuEntity implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 179147156365414499L;

    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, length = 10)
    private Integer id;

    /** 菜单姓名 */
    @Column(name = "name", nullable = true, length = 255)
    private String name;

    /** 父级菜单ID */
    @Column(name = "parent_id", nullable = true, length = 10)
    private Integer parentId;

    /** 位置序号 */
    @Column(name = "order_", nullable = true, length = 10)
    private Integer order;

    /** 层级 */
    @Column(name = "level", nullable = true, length = 10)
    private Integer level;

    /** 路径 */
    @Column(name = "url", nullable = true, length = 255)
    private String url;

    /** 图片 */
    @Column(name = "icon", nullable = true, length = 255)
    private String icon;

    /** 创建时间 */
    @Column(name = "create_time", nullable = true, length = 19)
    private Long createTime;

    /** 更新时间 */
    @Column(name = "update_time", nullable = true, length = 19)
    private Long updateTime;

    /** 创建人 */
    @Column(name = "creator", nullable = true, length = 10)
    private Integer creator;

    /** 有效标记 1 有效 0无效 */
    @Column(name = "flag", nullable = true, length = 10)
    private Integer flag;

    /**
     * 获取id
     * 
     * @return id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置id
     * 
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取菜单姓名
     * 
     * @return 菜单姓名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置菜单姓名
     * 
     * @param name
     *          菜单姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取父级菜单ID
     * 
     * @return 父级菜单ID
     */
    public Integer getParentId() {
        return this.parentId;
    }

    /**
     * 设置父级菜单ID
     * 
     * @param parentId
     *          父级菜单ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取位置序号
     * 
     * @return 位置序号
     */
    public Integer getOrder() {
        return this.order;
    }

    /**
     * 设置位置序号
     * 
     * @param order
     *          位置序号
     */
    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * 获取层级
     * 
     * @return 层级
     */
    public Integer getLevel() {
        return this.level;
    }

    /**
     * 设置层级
     * 
     * @param level
     *          层级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取路径
     * 
     * @return 路径
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * 设置路径
     * 
     * @param url
     *          路径
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取图片
     * 
     * @return 图片
     */
    public String getIcon() {
        return this.icon;
    }

    /**
     * 设置图片
     * 
     * @param icon
     *          图片
     */
    public void setIcon(String icon) {
        this.icon = icon;
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
     * 获取更新时间
     * 
     * @return 更新时间
     */
    public Long getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置更新时间
     * 
     * @param updateTime
     *          更新时间
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取创建人
     * 
     * @return 创建人
     */
    public Integer getCreator() {
        return this.creator;
    }

    /**
     * 设置创建人
     * 
     * @param creator
     *          创建人
     */
    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    /**
     * 获取有效标记 1 有效 0无效
     * 
     * @return 有效标记 1 有效 0无效
     */
    public Integer getFlag() {
        return this.flag;
    }

    /**
     * 设置有效标记 1 有效 0无效
     * 
     * @param flag
     *          有效标记 1 有效 0无效
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}