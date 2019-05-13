package tk.mybatis.springboot.modules.sys.vo;


import tk.mybatis.springboot.modules.sys.model.Resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @description：UserVo
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
public class MenuRightVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    /**
     * 资源图标
     */
    private String icon;
    /**
     * 父级资源id
     */
    private String pid;
    private Integer resourceType;
    private List<RightChildVo> functions;

    public MenuRightVo(Resource resource) {
        this.id = resource.getId();
        this.name = resource.getName();
        this.icon = resource.getIcon();
        this.pid = resource.getPid();
        this.resourceType = resource.getResourceType();
        this.functions = new ArrayList<RightChildVo>();
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

    public List<RightChildVo> getFunctions() {
        return functions;
    }

    public void setFunctions(List<RightChildVo> functions) {
        this.functions = functions;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }
}