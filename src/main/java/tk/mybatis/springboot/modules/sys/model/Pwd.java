package tk.mybatis.springboot.modules.sys.model;

import java.io.Serializable;

/**
 * 密码
 */
public class Pwd implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String loginName;
    private String oldPwd;
    private String newPwd;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
