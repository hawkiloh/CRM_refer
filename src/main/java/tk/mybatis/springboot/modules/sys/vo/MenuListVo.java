package tk.mybatis.springboot.modules.sys.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import tk.mybatis.springboot.modules.sys.model.Resource;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @description：UserVo
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
public class MenuListVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 资源图标
     */
    @JsonProperty("iconCls")
    private String icon;

    /**
     * 父级资源id
     */
    private String pid;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 状态
     */
    private String status;

    /**
     * 资源类别
     */
    private String resourceType;

    private List<MenuListVo> children;

    public MenuListVo(Resource resource) {
        this.id = resource.getId();
        this.name = resource.getName();
        this.url = resource.getUrl();
        this.icon = resource.getIcon();
        this.pid = resource.getPid();
        this.seq = resource.getSeq();
        if (resource.getStatus() == 0) {
            this.status = "启动";
        } else {
            this.status = "停用";
        }
        if (resource.getResourceType() == 0) {
            this.resourceType = "菜单";
        } else {
            this.resourceType = "按钮";
        }
        this.children = new ArrayList<MenuListVo>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
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
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public List<MenuListVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuListVo> children) {
        this.children = children;
    }
}