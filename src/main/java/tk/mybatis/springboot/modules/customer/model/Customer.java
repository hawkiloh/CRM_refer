package tk.mybatis.springboot.modules.customer.model;

import tk.mybatis.springboot.common.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户开发管理
 */
public class Customer extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String region;
    private String managerId;
    private String level;
    private String satify;
    private String credit;
    private String state;
    private String tel;
    private String fax;

    //生日、邮箱
    private Date birthday;
    private String email;


    public Customer() {
    }

    public Customer(String name) {
        this.name = name;
    }

    public Customer(String name, String region, String managerId, String level, String satify, String credit, String state, String tel, String fax, Date birthday, String email) {
        this.name = name;
        this.region = region;
        this.managerId = managerId;
        this.level = level;
        this.satify = satify;
        this.credit = credit;
        this.state = state;
        this.tel = tel;
        this.fax = fax;
        this.birthday = birthday;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", managerId='" + managerId + '\'' +
                ", level='" + level + '\'' +
                ", satify='" + satify + '\'' +
                ", credit='" + credit + '\'' +
                ", state='" + state + '\'' +
                ", tel='" + tel + '\'' +
                ", fax='" + fax + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                '}';
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSatify() {
        return satify;
    }

    public void setSatify(String satify) {
        this.satify = satify;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }


}
