package tk.mybatis.springboot.modules.sys.model;


import tk.mybatis.springboot.common.BaseEntity;
import tk.mybatis.springboot.modules.sys.vo.UserVo;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 */
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登陆名
     */
    private String loginName;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码加密盐
     */
    private String salt;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户类别
     */
    private Integer userType;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 所属机构
     */
    private String organizationId;

    /**
     * 创建时间
     */
    private Date createTime;

    public User(UserVo vo) {
        this.loginName = vo.getLoginName();
        this.name = vo.getName();
        this.password = vo.getPassword();
        this.salt = vo.getSalt();
        this.sex = vo.getSex();
        this.age = vo.getAge();
        this.phone = vo.getPhone();
        this.userType = vo.getUserType();
        this.status = vo.getStatus();
        this.organizationId = vo.getOrganizationId();
    }

    public User() {
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getSex() {
        return this.sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUserType() {
        return this.userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}
