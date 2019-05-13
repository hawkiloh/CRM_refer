package tk.mybatis.springboot.modules.sys.vo;


import tk.mybatis.springboot.modules.sys.model.Resource;

import java.io.Serializable;


/**
 * @description：UserVo
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
public class RightChildVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String action;
    private String pid;
    private Boolean checked;

    public RightChildVo(Resource resource) {
        this.id = resource.getId();
        this.name = resource.getName();
        if ("新增".equalsIgnoreCase(resource.getName())) {
            this.action = "add";
        } else if ("删除".equalsIgnoreCase(resource.getName())) {
            this.action = "remove";
        } else if ("编辑".equalsIgnoreCase(resource.getName())) {
            this.action = "edit";
        } else if ("列表".equalsIgnoreCase(resource.getName())) {
            this.action = "list";
        } else if ("授权".equalsIgnoreCase(resource.getName())) {
            this.action = "right";
        } else {
            this.action = "";
        }
        this.pid = resource.getPid();
        this.checked = false;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}