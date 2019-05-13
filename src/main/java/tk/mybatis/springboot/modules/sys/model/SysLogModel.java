package tk.mybatis.springboot.modules.sys.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志
 */
public class SysLogModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 10;

    /**
     * 内容
     */
    private String msg;

    /**
     * 创建时间
     */
    private Date createTime;

    private String method;

    //参数
    private String params;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 执行时长(毫秒)
     */
    private Long time;

    private String userName;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
