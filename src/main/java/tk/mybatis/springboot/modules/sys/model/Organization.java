package tk.mybatis.springboot.modules.sys.model;

import org.hibernate.validator.constraints.NotBlank;
import tk.mybatis.springboot.common.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 组织机构
 */
public class Organization extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组织名
     */
    @NotBlank
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 编号
     */
    @NotBlank
    private String code;

    /**
     * 图标
     */
    private String icon;

    /**
     * 父级主键
     */
    private String pid;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 创建时间
     */
    private Date createTime;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getSeq() {
        return this.seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
