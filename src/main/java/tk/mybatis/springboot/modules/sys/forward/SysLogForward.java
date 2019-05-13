package tk.mybatis.springboot.modules.sys.forward;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SysLogForward {

    @RequestMapping(value = "/sysLog/address")
    public String address_map() {
        return "/sys/address/map";
    }

    @RequestMapping(value = "/sysLog/manager")
    public String sysLog_manager() {
        return "/sys/sysLog/sysLog_manage";
    }

}
