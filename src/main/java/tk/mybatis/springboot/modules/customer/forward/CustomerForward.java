package tk.mybatis.springboot.modules.customer.forward;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerForward {

    @RequestMapping(value = "/customer/manager")
    public String customer_manager() {
        return "/customer/custom/customer_manage";
    }

    @RequestMapping(value = "/customer/addUI")
    public String customer_add() {
        return "/customer/custom/add";
    }

    @RequestMapping(value = "/customer/drains")
    public String customer_drains() {
        return "/customer/custom/customerDrains";
    }

}
