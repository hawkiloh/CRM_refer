package tk.mybatis.springboot.modules.sys.model;

import java.io.Serializable;

/**
 * 用户角色
 */
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 角色id
     */
    private String roleId;


    public UserRole(String id, String userId, String roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserRole() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
