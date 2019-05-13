package tk.mybatis.springboot.modules.sys.model;

import java.io.Serializable;

/**
 * 角色资源
 */
public class RoleResource implements Serializable {

    private static final Long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 资源id
     */
    private String resourceId;

    public RoleResource(String id, String roleId, String resourceId) {
        this.id = id;
        this.roleId = roleId;
        this.resourceId = resourceId;
    }

    public RoleResource() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return this.resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

}
