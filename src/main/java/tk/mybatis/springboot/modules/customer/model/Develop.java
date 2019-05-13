package tk.mybatis.springboot.modules.customer.model;

import tk.mybatis.springboot.common.BaseEntity;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户开发管理
 */
@Table(name = "sales_chance")
public class Develop extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String custName;
    private String title;
    private String contact;
    private String contactTel;
    private String createBy;
    private Date createDate;
    private String designee;
    private String description;
    private String status;
    private Date designeeDate;


    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDesignee() {
        return designee;
    }

    public void setDesignee(String designee) {
        this.designee = designee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDesigneeDate() {
        return designeeDate;
    }

    public void setDesigneeDate(Date designeeDate) {
        this.designeeDate = designeeDate;
    }
}
