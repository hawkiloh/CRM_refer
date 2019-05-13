package tk.mybatis.springboot.modules.sys.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import tk.mybatis.springboot.modules.sys.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;

public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 10;

    @NotBlank
    @Length(min = 4, max = 64)
    private String loginName;

    private String name;

    @JsonIgnore
    private String password;
    @JsonIgnore
    private String salt; // 密码加密盐

    private Integer sex;

    private Integer age;

    private Integer userType;

    private Integer status;

    private String organizationId;

    private String createTime;

    private String phone;

    private String roles;

    private String organizationName;

    private String roleIds;

    public UserVo(User user) {
        this.id = user.getId();
        this.page = user.getPage();
        this.rows = user.getRows();
        this.loginName = user.getLoginName();
        this.name = user.getName();
        this.password = user.getPassword();
        this.salt = user.getSalt();
        this.sex = user.getSex();
        this.age = user.getAge();
        this.userType = user.getUserType();
        this.status = user.getStatus();
        this.organizationId = user.getOrganizationId();
        if (user.getCreateTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            this.createTime = sdf.format(user.getCreateTime());
        }
        this.phone = user.getPhone();
    }

    public UserVo() {
    }

    public UserVo(Integer page, Integer rows, String loginName, String name, String password, String salt, Integer sex, Integer age, Integer userType, Integer status, String organizationId, String createTime, String phone, String roles, String organizationName, String roleIds) {
        this.page = page;
        this.rows = rows;
        this.loginName = loginName;
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.sex = sex;
        this.age = age;
        this.userType = userType;
        this.status = status;
        this.organizationId = organizationId;
        this.createTime = createTime;
        this.phone = phone;
        this.roles = roles;
        this.organizationName = organizationName;
        this.roleIds = roleIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
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


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getStatus() {
        return status;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }
}