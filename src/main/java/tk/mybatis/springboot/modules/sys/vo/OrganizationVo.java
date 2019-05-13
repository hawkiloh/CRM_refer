package tk.mybatis.springboot.modules.sys.vo;


import org.hibernate.validator.constraints.NotBlank;
import tk.mybatis.springboot.modules.sys.model.Organization;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * 组织机构
 */
public class OrganizationVo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 10;

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
    private String iconCls;

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
    private String createTime;

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

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public OrganizationVo() {
    }

    public OrganizationVo(Organization org) {
        this.id = org.getId();
        this.page = org.getPage();
        this.rows = org.getRows();
        this.name = org.getName();
        this.address = org.getAddress();
        this.code = org.getCode();
        this.iconCls = org.getIcon();
        this.pid = org.getPid();
        this.seq = org.getSeq();
        if (org.getCreateTime() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            this.createTime = sdf.format(org.getCreateTime());
        }
    }


}
