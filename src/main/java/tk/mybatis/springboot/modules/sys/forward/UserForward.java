package tk.mybatis.springboot.modules.sys.forward;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserForward {

    @RequestMapping(value = "/user/manager")
    public String user_manager() {
        return "/sys/user/manager";
    }

    @RequestMapping(value = "/user/addUI")
    public String user_add() {
        return "/sys/user/add";
    }


}
