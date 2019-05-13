package tk.mybatis.springboot.modules.sys.vo;


import tk.mybatis.springboot.modules.sys.model.Resource;

import java.io.Serializable;


/**
 * @description：UserVo
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
public class MenuVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    /**
     * 资源名称
     */
    private String text;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 资源图标
     */
    private String iconCls;

    /**
     * 父级资源id
     */
    private String pid;

    /**
     * 打开的
     */
    private Boolean expanded;

    public MenuVo(Resource resource) {
        this.id = resource.getId();
        this.text = resource.getName();
        this.url = resource.getUrl();
        this.iconCls = resource.getIcon();
        this.pid = resource.getPid();
        if (resource.getOpened() == 1) {
            this.expanded = false;
        } else {
            this.expanded = true;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
}