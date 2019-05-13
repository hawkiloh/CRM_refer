package tk.mybatis.springboot.modules.sys.forward;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoleForward {

    @RequestMapping(value = "/role/manager")
    public String role_manager() {
        return "/sys/role/manager";
    }

    @RequestMapping(value = "/role/addUI")
    public String role_add() {
        return "/sys/role/add";
    }

    @RequestMapping(value = "/role/rightUI")
    public String role_right() {
        return "/sys/role/right";
    }
}
