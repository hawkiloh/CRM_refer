package tk.mybatis.springboot.modules.customer.model;

import tk.mybatis.springboot.common.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户开发管理
 */
public class CustomerDrains extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String status;
    private String delay;
    private String reason;
    private Date drainDate;
    private String customerName;
    private String customerId;
    private Date lastOrderDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getDrainDate() {
        return drainDate;
    }

    public void setDrainDate(Date drainDate) {
        this.drainDate = drainDate;
    }


    public Date getLastOrderDate() {
        return lastOrderDate;
    }

    public void setLastOrderDate(Date lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
